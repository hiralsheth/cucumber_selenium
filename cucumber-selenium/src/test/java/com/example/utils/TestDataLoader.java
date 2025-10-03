// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Paths;
// import java.util.ArrayList;
// import java.util.List;

// import org.json.JSONArray;
// import org.json.JSONObject;
// import org.w3c.dom.Document;
// import org.w3c.dom.NodeList;

// import javax.xml.parsers.DocumentBuilder;
// import javax.xml.parsers.DocumentBuilderFactory;

// public class TestDataLoader {

//     public List<String[]> loadCSV(String filePath) throws IOException {
//         List<String[]> data = new ArrayList<>();
//         try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//             String line;
//             while ((line = br.readLine()) != null) {
//                 data.add(line.split(","));
//             }
//         }
//         return data;
//     }

//     public List<JSONObject> loadJSON(String filePath) throws IOException {
//         List<JSONObject> jsonObjects = new ArrayList<>();
//         String content = new String(Files.readAllBytes(Paths.get(filePath)));
//         JSONArray jsonArray = new JSONArray(content);
//         for (int i = 0; i < jsonArray.length(); i++) {
//             jsonObjects.add(jsonArray.getJSONObject(i));
//         }
//         return jsonObjects;
//     }

//     public List<String[]> loadXML(String filePath) throws Exception {
//         List<String[]> data = new ArrayList<>();
//         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//         DocumentBuilder builder = factory.newDocumentBuilder();
//         Document document = builder.parse(filePath);
//         NodeList nodeList = document.getElementsByTagName("user"); // Assuming 'user' is the tag for each entry

//         for (int i = 0; i < nodeList.getLength(); i++) {
//             String username = nodeList.item(i).getChildNodes().item(0).getTextContent();
//             String password = nodeList.item(i).getChildNodes().item(1).getTextContent();
//             data.add(new String[]{username, password});
//         }
//         return data;
//     }
// }