package gov.irs.aca.brmg;


import java.io.*;

import javax.activation.DataHandler;
import javax.xml.ws.soap.MTOM;
import javax.jws.WebService;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PutMethod;


@MTOM(enabled=true)
@WebService(
		endpointInterface = "gov.irs.aca.brmg.UploadService",
		serviceName="UploadService"
		
		)
public class UploadServiceImpl implements UploadService {
	
    private static String url = "http://localhost:8081/rest/teams/eagles";

	public void uploadFile(FileUploader Dfile) {
		
		DataHandler dh = Dfile.getDfile();
		
		Runtime runtime = Runtime.getRuntime();
		
		int mb = 1024* 1024;
		
		System.out.println("before free " + ( runtime.freeMemory() /mb));
		
		try{
			
		
		InputStream is = dh.getInputStream();
		
HttpClient client = new HttpClient();
                
        InputStreamRequestEntity isre =  new InputStreamRequestEntity(is);
        
        PutMethod putMethod = new PutMethod(url);

      putMethod.setContentChunked(true);
       
       putMethod.setRequestEntity(isre);

        putMethod.setRequestHeader("Content-type", "text/xml; charset=ISO-8859-1");

        int statusCode1 = client.executeMethod(putMethod);

        System.out.println("statusLine>>>" + putMethod.getStatusLine());
        
        putMethod.releaseConnection();
        
        is.close();
        
        System.out.println("after free " + ( runtime.freeMemory() /mb));
				
		}
		catch(Exception e){
			
			e.printStackTrace();
		}
		

	}

}
