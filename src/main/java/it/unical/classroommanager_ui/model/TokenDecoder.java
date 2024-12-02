package it.unical.classroommanager_ui.model;

import java.util.Base64;


public class TokenDecoder {

    private String header;
    private String payload;

    public TokenDecoder(String token){

        String[] chunks = token.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();

        header = new String(decoder.decode(chunks[0]));
        payload = new String(decoder.decode(chunks[1]));

    }

    public String serialNumber(){
        return getChosenField("serialNumber");
    }

    public String role(){
        return getChosenField("role");
    }

    public String email(){
        return getChosenField("email");
    }

    public String name() { return getChosenField("name");}

    public String surname() { return getChosenField("surname");}

    public String sub(){
        return getChosenField("sub");
    }

    private String getChosenField(String fieldName) {
        String fieldPattern = "\"" + fieldName + "\":";
        int fieldStartIndex = payload.indexOf(fieldPattern);

        if (fieldStartIndex == -1) {
            return "Field not found";
        }


        fieldStartIndex += fieldPattern.length();


        if (payload.charAt(fieldStartIndex) == '"') {

            int fieldEndIndex = payload.indexOf("\"", fieldStartIndex + 1);
            if (fieldEndIndex == -1) {
                return "Field value not found";
            }
            return payload.substring(fieldStartIndex + 1, fieldEndIndex);
        }
        else {

            int fieldEndIndex = payload.indexOf(",", fieldStartIndex);
            if (fieldEndIndex == -1) {

                fieldEndIndex = payload.indexOf("}", fieldStartIndex);
            }
            if (fieldEndIndex == -1) {
                return "Field value not found";
            }
            return payload.substring(fieldStartIndex, fieldEndIndex).trim();
        }

    }






}
