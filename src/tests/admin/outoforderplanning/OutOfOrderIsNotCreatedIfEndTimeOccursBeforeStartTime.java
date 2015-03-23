package tests.admin.outoforderplanning;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomOutOfOrderPlanningPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.utils.readers.ExcelReader;

/**
 * TC21: Verify an Out Of Order cannot be created when end time (hours and minutes) 
 * occurs before start time
 * TC40: Verify when end time (hours and minutes) occurs before start time an error message 
 * that says 'To' field must be greater than 'From' field is displayed
 * 
 * @author Yesica Acha
 *
 */
public class OutOfOrderIsNotCreatedIfEndTimeOccursBeforeStartTime {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	String roomName = testData.get(1).get("Room Name");
	String startDate = testData.get(1).get("Start date");
	String endDate = testData.get(1).get("End date");
	String startTime = testData.get(1).get("Start time (minutes to add)");
	String endTime = testData.get(1).get("End time (minutes to add)");
	
	@Test
	public void testOutOfOrderIsNotCreatedIfEndTimeOccursBeforeStartTime() {
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage rooms = homeAdminPage.clickConferenceRoomsLink();
		RoomOutOfOrderPlanningPage outOfOrder = rooms
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();
		outOfOrder.setStartDateWithCalendar(startDate)
		.setEndDateWithCalendar(endDate)
		.setStartTime(startTime)
		.setEndTime(endTime)
		.clickSaveWithErrorBtn();
		
		//Assertion for TC21
		Assert.assertTrue(outOfOrder.isErrorMessagePresent());
		
		//Assertion for TC40
		Assert.assertTrue(outOfOrder.isToGreaterThanFromErrorDisplayed());
		outOfOrder.clickCancelBtn();
	}
}
