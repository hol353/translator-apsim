package org.agmip.translators.apsim;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.agmip.common.Functions;
import org.agmip.util.JSONAdapter;
import org.agmip.util.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Meng Zhang
 */
public class ApsimCmdApp {

    private static boolean isCompressed = false;
    private static boolean isToModel = false;
    private static ArrayList<String> inputPaths = new ArrayList();
    private static String outputPath = getOutputPath("");
    private static final Logger LOG = LoggerFactory.getLogger(ApsimCmdApp.class);

    public static void main(String... args) throws IOException {
        readCommand(args);
        if (inputPaths == null) {
            LOG.error("There is no valid input path, please check input arguments");
            return;
        }
        if (isToModel) {
            LOG.info("Translate {} to APSIM...", inputPaths);
            ApsimWriter translator = new ApsimWriter();
            translator.writeFile(outputPath, readJson());
            if (isCompressed) {
                createZip();
            }
            LOG.info("Job done!");
        } else {
            LOG.info("Translate {} to JSON...", inputPaths);
            LOG.warn("It is not implemented yet!");
//            ApsimReader translator = new ApsimReader();
//            Map result = translator.readFile(inputPath);
//            BufferedOutputStream bo;
//            if (!outputPath.equals("")) {
//                outputPath = new File(outputPath).getPath() + File.separator;
//            }
//            File f = new File(outputPath + new File(outputPath).getName().replaceAll("\\.\\w+$", ".json"));
//            bo = new BufferedOutputStream(new FileOutputStream(f));
//
//            // Output json for reading
//            bo.write(JSONAdapter.toJSON(result).getBytes());
//            bo.flush();
//            bo.close();
        }
    }

    private static void readCommand(String[] args) {

        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("-zip")) {
                isCompressed = true;
            } else if (args[i].toUpperCase().endsWith(".JSON")) {
                isToModel = true;
                inputPaths.add(args[i]);
            } else if (args[i].toUpperCase().endsWith(".ZIP")) {
                isToModel = false;
                inputPaths.add(args[i]);
            } else {
                outputPath = getOutputPath(args[i]);
            }
        }
        LOG.info("Read from {}", inputPaths);
        LOG.info("Output to {}", outputPath);
    }

    private static Map readJson() throws IOException {
        Map data = new HashMap();
        for (String in : inputPaths) {
            data.putAll(JSONAdapter.fromJSONFile(in));
        }
        fixData(data);
        return data;
    }
    
    private static void createZip() throws FileNotFoundException, IOException {

        File[] files = new File(outputPath).listFiles();
        Calendar cal = Calendar.getInstance();
        File outputFile = new File(outputPath + File.separator + "AGMIP_APSIM_" + cal.getTimeInMillis() + ".zip");
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outputFile));
        ZipEntry entry;
        BufferedInputStream bis;
        byte[] data = new byte[1024];
        LOG.info("Start zipping all the files...");
        
        // Check if there is file been created
        if (files == null || files.length == 0) {
            LOG.warn("No files here for zipping");
            return;
        }

        for (File file : files) {
            if (file == null) {
                continue;
            } else if (file.getName().toUpperCase().endsWith(".ZIP")) {
                continue;
            }

            if (outputPath != null) {
                entry = new ZipEntry(file.getPath().substring(new File(outputPath).getPath().length() + 1));
            } else {
                entry = new ZipEntry(file.getPath());
            }
            out.putNextEntry(entry);
            bis = new BufferedInputStream(new FileInputStream(file));

            int count;
            while ((count = bis.read(data)) != -1) {
                out.write(data, 0, count);
            }
            bis.close();
            file.delete();
        }

        out.close();
        LOG.info("End zipping");
    }

    private static String getOutputPath(String arg) {
        
        String path;
        if (arg != null && !"".equals(arg)) {
            path = new File(arg).getPath() + File.separator + "APSIM";
        } else {
            path = "APSIM";
        }
        
        File dir = new File(path);
        int cnt = 1;
        while (dir.exists() && dir.list().length > 0) {
            dir = new File(path + "-" + cnt);
            cnt++;
        }
        return dir.getPath();
    }
    
    private static void fixData(Map data) {
        ArrayList<HashMap> exps = MapUtil.getObjectOr(data, "experiments", new ArrayList());
        HashSet soilIds = getIds(MapUtil.getObjectOr(data, "soils", new ArrayList()), "soil_id");
        HashSet wstIds = getIds(MapUtil.getObjectOr(data, "weathers", new ArrayList()), "wst_id");
        for (HashMap exp : exps) {
            fixDataLink(exp, soilIds, "soil_id", 10);
            fixDataLink(exp, wstIds, "wst_id", 4);
            fixEventDate(exp);
        }
    }
    
    private static HashSet<String> getIds(ArrayList<HashMap> arr, String idName) {
        HashSet ids = new HashSet();
        for (HashMap data : arr) {
            String id = (String) data.get(idName);
            if (id != null) {
                ids.add(id);
            }
        }
        return ids;
    }
    
    private static void fixDataLink(HashMap expData, HashSet ids, String idName, int adjustLength) {
        
        String id = (String) expData.get(idName);
        boolean isIdFixed = false;
        if (id != null && !ids.isEmpty()) {
            while (!ids.contains(id)) {
                if (id.length() > adjustLength) {
                    id = id.substring(0, adjustLength);
                    isIdFixed = true;
                } else {
                    isIdFixed = false;
                    break;
                }
            }
        }
        if (isIdFixed) {
            LOG.info("Fix {} to {} for experiment {}", idName, id, MapUtil.getValueOr(expData, "exname", "N/A"));
            expData.put(idName, id);
        }
    }
    
    private static void fixEventDate(HashMap expData) {
        ArrayList<HashMap<String, String>> events = MapUtil.getBucket(expData, "management").getDataList();
        String pdate = getPdate(events);
        if (pdate == null) {
            LOG.warn("Planting date is missing, will quit fixing event date");
            return;
        }
        String exname = MapUtil.getValueOr(expData, "exname", "N/A");
        for (HashMap<String, String> event : events) {
            String date = event.get("date");
            if (date != null && date.length() != 8) {
                String newDate = Functions.dateOffset(pdate, date);
                event.put("date", newDate);
                LOG.info("Fix {} date to {} for experiment {}", event.get("event"), newDate, exname);
            }
        }
    }
    
    private static String getPdate(ArrayList<HashMap<String, String>> events) {
        for (HashMap<String, String> event : events) {
            if ("planting".equals(event.get("event"))) {
                return event.get("date");
            }
        }
        return null;
    }
}
