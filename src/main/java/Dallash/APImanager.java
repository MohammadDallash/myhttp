package Dallash;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;

import java.io.IOException;



public class APImanager {

    private XSSFWorkbook wb = null;
    private final int EOF;
    private JSONObject APIs = new JSONObject();
    private int iterator;

    public APImanager(String S, int eof)
    {
        this.EOF = eof;
        try
        {
            wb = ExcelRead.get_Workbook(S);
        } catch (IOException e)
        {
            System.out.println("err reading the file\n");
        }
    }


    public JSONObject get_api()
    {
        String Current_cell;

        //for (this.iterator = 0; iterator < this.EOF; this.iterator++)
        {
            Current_cell = ExcelRead.read_Cell(iterator, 0, 0, wb);
            //if (true)
            {
                String APIname = Current_cell.replace("(API_NAME)","");
                JSONObject APIvalue = new JSONObject();

                APIvalue.put(ExcelRead.read_Cell(iterator+1, 0, 0, wb), ExcelRead.read_Cell(iterator+2, 0, 0, wb));
                APIvalue.put(ExcelRead.read_Cell(iterator+1, 1, 0, wb), ExcelRead.read_Cell(iterator+2, 1, 0, wb));

                JSONObject requestJSONObject = new JSONObject();
                JSONObject responseJSONObject = new JSONObject();


                recursion(null , requestJSONObject, responseJSONObject, this.iterator+5);

                APIvalue.put("request",requestJSONObject);
                APIvalue.put("response",responseJSONObject);


                this.APIs.put(APIname, APIvalue);
                return this.APIs;

            }
        }



    }



    public  void recursion(JSONObject JSONobject,JSONObject requestObject,JSONObject responseObject,int i )
    {
        i++;
        if (i>=this.EOF)
        {
            this.iterator = i;
            return;
        }

        String Cureent_cell = ExcelRead.read_Cell(i, 1, 0, wb);
        String type = ExcelRead.read_Cell(i, 2, 0, wb);
        String[] array =Cureent_cell.split("/");
        String last_value = array[array.length - 1];

        if (type.equals("string"))
        {
            if(array.length > 2) JSONobject.put(last_value, get_information(i));
            if (array.length == 2)
            {
                if(isRequest(i)) requestObject.put(last_value, get_information(i));
                else responseObject.put(last_value, get_information(i));
            }
            recursion( JSONobject, requestObject, responseObject,i);
        }
        else
        {
            JSONObject inner = new JSONObject();
            recursion( inner, requestObject, responseObject,i);
            if(array.length > 2) JSONobject.put(last_value, inner);
            if (array.length == 2)
            {
                if(isRequest(i)) requestObject.put(last_value, inner);
                else responseObject.put(last_value, inner);
            }
        }


    }






    public  boolean isRequest(int x)
    {
        String Cureent_cell = ExcelRead.read_Cell(x, 0, 0, wb);
        if (Cureent_cell.equals("I")) return true;
        if (Cureent_cell.equals("O")) return false;
        else return false;
    }


    public JSONObject get_information(int x)
    {
        JSONObject myJson = new JSONObject();
        myJson.put("Type", ExcelRead.read_Cell(x, 2, 0, wb));
        myJson.put("Allowed Values", ExcelRead.read_Cell(x, 3, 0, wb));
        myJson.put("Mandatory", ExcelRead.read_Cell(x, 4, 0, wb));

        return myJson;
    }



}
