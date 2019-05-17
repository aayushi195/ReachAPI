package edu.asu.heal.core.api.resources;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

// This class uses command line arguments as below:
// 0 - server url (https://<IP>:<PORT>/ComapassAPI/rest/
// 1 - number of new patients and schedule for those patients needs to be created
// 2 - trial id ( Currently only one trial id is used to create patients -5a946ff566684905df608446)
// 3 - Date (optional) "Fri May 6 20:12:38 MST 2019"

public class PatientAndScheduleCreator {

	public static void main(String[] args) {
		try {
			if(args.length < 3) {
				System.out.println(" Please add all required command line args as in below order : ");
				System.out.println("1st - Server URL with only context (/CompassAPI/rest/ - Please add last back slash as well.)");
				System.out.println("2nd - a number representing how many new patients and "
						+ "schedule for those patients needs to be created");
				System.out.println("3rd - trial id (Currently for comapass only "
						+ "5a946ff566684905df608446 used as trial id) ");
				System.out.println("4th - Date from where you want to create a schedule "
						+ "(OPTIONAL)(Use Format :\"Fri May 6 20:12:38 MST 2019\"");
				return;
			}
			Client compassClient = Client.create();
			Integer numberOfPatients = Integer.parseInt(args[1]);
			String trialId =args[2];// "{\"patientPin\":\""+year+"\",\"subject\":\""+subject+"\"}";
			String startDate = null;
			if(args.length == 4)
				startDate = args[3];
			List<Integer> patientPins = new ArrayList<>();
			System.out.println("Are you wan to create "+numberOfPatients+" patients ? (Y/N)");
			BufferedReader reader =  
					new BufferedReader(new InputStreamReader(System.in));
			String res = reader.readLine();
			JSONObject jobj;
			if(res.equalsIgnoreCase("y") || res.equalsIgnoreCase("yes")) {
				String patientResourceURL = args[0] + "patients";
				WebResource webPatient = compassClient.resource(patientResourceURL);
				for(int i=0; i< numberOfPatients;i++ ) {
					ClientResponse patientRes = webPatient.type("text/plain")
							.post(ClientResponse.class, trialId);
					Status resStatus =patientRes.getClientResponseStatus();
					if(resStatus.getStatusCode() != 201) {
						System.out.println("Some error in calling create Patient API");
						System.out.println(patientRes.getEntity(String.class));
						return;
					}
					jobj = new JSONObject(patientRes.getEntity(String.class));
					JSONObject patientJSON = jobj.getJSONObject("patient");
					Integer pin = patientJSON.getInt("pin");
					patientPins.add(pin);
				}
				System.out.println(numberOfPatients+" Patients have been created starting from pin : "+patientPins.get(0));
				System.out.println("Do you wan to create schedule for all these patients ? (Y/N)");
				String res1 = reader.readLine();
				if(res1.equalsIgnoreCase("y") || res1.equalsIgnoreCase("yes")) {
					String scheduleResourceURL = args[0] + "schedules";
					WebResource webPatientSchedule = compassClient.resource(scheduleResourceURL);
					for(Integer i : patientPins) {
						String payload = "{\"patientPin\":\""+i+"\",\"startDate\":\""+startDate+"\"}";
						ClientResponse scheduleRes = webPatient.type("application/json")
								.post(ClientResponse.class, payload);
						Status resStatus =scheduleRes.getClientResponseStatus();
						if(resStatus.getStatusCode() != 201) {
							System.out.println("Some error in calling create patient schedule API");
							System.out.println(scheduleRes.getEntity(String.class));
							return;
						}
					}
					System.out.println("You have sucessfully create patients and their schudules.");
					System.out.println("Bye.");
				}else {
					System.out.println("You have sucessfully create patients.");
					System.out.println("Adios...");
				}
			}else {
				System.out.println("Good bye...");
			}
		}catch(Exception e) {
			System.out.println("OPPSSS ...GOT BELOW EXCEPTION..");
			e.printStackTrace();
		}
	}

}
