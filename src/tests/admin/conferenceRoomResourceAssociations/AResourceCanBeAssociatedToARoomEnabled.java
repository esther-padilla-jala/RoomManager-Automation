package tests.admin.conferenceRoomResourceAssociations;
import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.common.UIMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;
import framework.utils.readers.JsonReader;

/**
 * TC02: Verify that a resource can be associated to a room enabled.
 * @author Juan Carlos Guevara
 */
public class AResourceCanBeAssociatedToARoomEnabled {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("APIResources");
	String roomName = testData.get(0).get("Room Name");

	//Reading json resource information
	JsonReader jsonValue = new JsonReader();
	String resourceFileJSON = "\\src\\tests\\Resource1.json";
	String filePath = System.getProperty("user.dir") + resourceFileJSON;
	String resourceDisplayName = jsonValue.readJsonFile("name" , resourceFileJSON);

	@BeforeClass
	public void precondition() throws MalformedURLException, IOException {
		HomeAdminPage homeAdminPage = new HomeAdminPage();				
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();
		if(resourcesPage.isResourceNameDisplayedInResourcesPage(resourceDisplayName)){
			RootRestMethods.deleteResource(resourceDisplayName);
			UIMethods.refresh();
		}

		//Create resource by Rest
		RootRestMethods.createResource(filePath, "");
		UIMethods.refresh();		
	}

	@Test(groups = {"ACCEPTANCE"})
	public void testAResourceCanBeAssociatedToARoomEnabled() {

		//Associate the resource to a room
		RoomsPage confRoomsPage = new RoomsPage();
		confRoomsPage.clickConferenceRoomsLink();
		RoomInfoPage infoPage = confRoomsPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage roomsResourceAssociationsPage = infoPage
				.clickResourceAssociationsLink();
		roomsResourceAssociationsPage.clickAddResourceToARoom(resourceDisplayName);
		confRoomsPage = roomsResourceAssociationsPage.clickSaveBtn();
		UIMethods.refresh();
		confRoomsPage.clickConferenceRoomsLink();

		//Assertion for TC02 
		Assert.assertTrue(roomsResourceAssociationsPage.searchResource(resourceDisplayName));	
	}

	@AfterClass
	public void postCondition() throws MalformedURLException, IOException {

		//delete resource with API rest method
		HomeAdminPage homeAdminPage = new HomeAdminPage();;	
		homeAdminPage.clickResourcesLink();
		RootRestMethods.deleteResource(testData.get(0).get("ResourceName"));
		UIMethods.refresh();
	}
}



