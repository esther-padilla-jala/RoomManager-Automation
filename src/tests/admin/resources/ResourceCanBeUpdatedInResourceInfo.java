package tests.admin.resources;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourceInfoPage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC02: Verify that display name of a resourcesPage can be updated in {ResourceInfo} form.  
 * TC04: Verify that name of a resourcesPage can be updated in {ResourceInfo} form .
 * TC05: Verify that icon of a resourcesPage can be updated in {ResourceInfo} form .
 * TC27: Verify that description of a resourcesPage can be updated and its new information is displayed in 
 * 		 {ResourceInfo} form. 
 * @author Marco Llano
 */
public class ResourceCanBeUpdatedInResourceInfo {
	private ResourcesPage resourcesPage;
	private ResourceInfoPage resourceInfoPage;
	
	//ExcelReader is used to read rooms data
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> resourcesDataList = excelReader.getMapValues("Resources");

	@Test(groups = {"FUNCTIONAL"})
	public void testResourceCanBeUpdatedInResourceInfo() throws InterruptedException {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		resourcesPage = homeAdminPage.clickResourcesLink();

		//Variables declaration and initialize
		String iconTitle = resourcesDataList.get(0).get("Icon");
		String resourceName = resourcesDataList.get(0).get("ResourceName");
		String resourceDisplayName = resourcesDataList.get(0).get("ResourceDisplayName");
		String resourceDescription = resourcesDataList.get(0).get("Description");
		String newIconTitle = resourcesDataList.get(1).get("Icon");
		String newResourceName = resourcesDataList.get(1).get("ResourceName");
		String newResourceDisplayName = resourcesDataList.get(1).get("ResourceDisplayName");
		String newResourceDescription = resourcesDataList.get(1).get("Description");

		//Create a resourcesPage
		ResourceCreatePage resourceCreatePage  = resourcesPage.clickAddResourceBtn();		
		resourcesPage = resourceCreatePage.clickResourceIcon()
				.selectResourceIcon(iconTitle)
				.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.setResourceDescription(resourceDescription)
				.clickSaveResourceBtn();

		//Update the created resourcesPage
		resourceInfoPage = resourcesPage.openResourceInfoPage(resourceName);
		resourcesPage = resourceInfoPage.clickResourceIcon()
				.selectResourceIcon(newIconTitle)
				.setResourceName(newResourceName)
				.setResourceDisplayName(newResourceDisplayName)
				.setResourceDescription(newResourceDescription)
				.clickSaveResourceBtn();
		resourceInfoPage = resourcesPage.openResourceInfoPage(newResourceDisplayName);

		//Assertion for TC02		
		Assert.assertTrue(resourceInfoPage.getResourceDisplayName().contains(newResourceDisplayName));

		//Assertion for TC04
		Assert.assertTrue(resourceInfoPage.getResourceName().contains(newResourceName));

		//Assertion for TC05
		Assert.assertTrue(resourceInfoPage.getResourceIcon(newIconTitle));

		//Assertion for TC27
		Assert.assertTrue(resourceInfoPage.getResourceDescription().contains(newResourceDescription));
	}	

	@AfterMethod(groups = {"FUNCTIONAL"})
	public void afterMethod() throws MalformedURLException, IOException {	
		resourcesPage = resourceInfoPage.clickCancelResourceBtn();		
		RootRestMethods.deleteResource(resourcesDataList.get(1).get("ResourceName"));
	}
}