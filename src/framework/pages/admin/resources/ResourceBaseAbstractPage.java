package framework.pages.admin.resources;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import framework.common.UIMethods;
import framework.selenium.SeleniumDriverManager;

/**
 * @author Marco Llano
 *
 */
public class ResourceBaseAbstractPage {
	protected WebDriver driver;
	protected WebDriverWait wait;

	@FindBy(xpath = "//div[@class='input-control text']/input[@ng-model='resource.name']") 
	WebElement resourceNameTxtBox;
	
	@FindBy(xpath = "//div[@class='input-control text']/input[@ng-model='resource.customName']") 
	WebElement resourceDisplayNameTxtBox;
	
	@FindBy(xpath = "//button[@ng-click='save()']") 
	WebElement saveResourceBtn;
	
	@FindBy(xpath = "//button[@ng-click='cancel()']") 
	WebElement cancelBtn;
	
	@FindBy(xpath = "//div[@class='input-control text']/textarea[@ng-model='resource.description']") 
	WebElement resourceDescriptionTxtBox;
	
	@FindBy(id = "convert") 
	WebElement resourceOpenIconBtn;

	@FindBy(xpath = "//button[@ng-click='cancel()']")
	WebElement closeCancelBtn;
	
	public ResourceBaseAbstractPage() {		
		driver = SeleniumDriverManager.getManager().getDriver();
		wait = SeleniumDriverManager.getManager().getWait();
		PageFactory.initElements(driver, this);
	}
	
	/**
	 * [ML]This method receives a resource name, then set this value into resource name field in resource info page
	 * @param resourceName
	 * @return
	 */
	public ResourceInfoPage setResourceName(String resourceName) {
		wait.until(ExpectedConditions.visibilityOf(resourceNameTxtBox));
		resourceNameTxtBox.clear();
		resourceNameTxtBox.sendKeys(resourceName);
		return new ResourceInfoPage();
	}
	
	/**
	 * [ML]This method receives a resource display name, then set this value into resource display name field in
	 *  resource info page
	 * @param resourceDisplayName
	 * @return the current page
	 */
	public ResourceInfoPage setResourceDisplayName(String resourceDisplayName) {
		resourceDisplayNameTxtBox.clear();
		resourceDisplayNameTxtBox.sendKeys(resourceDisplayName);
		return new ResourceInfoPage();
	}
	
	/**
	 * [ML]This method receives a resource description, then set this value into resource description field in 
	 * resource info page
	 * @param resourceDescription
	 * @return the current page
	 */
	public ResourceInfoPage setResourceDescription(String resourceDescription) {
		resourceDescriptionTxtBox.clear();
		resourceDescriptionTxtBox.sendKeys(resourceDescription);
		return new ResourceInfoPage();
	}
	
	/**
	 * [ML]This method receives a resource icon value, then set click on icon button
	 * @param iconTitle
	 * @return the current page
	 */
	public ResourceInfoPage selectResourceIcon(String iconTitle) {
		By icon = By.xpath("//button[@value='" + iconTitle +"']"); 
		wait.until(ExpectedConditions.elementToBeClickable(icon));
		driver.findElement(icon).click();
		return new ResourceInfoPage();
	}
	
	/**
	 * [ML]This method receives a direction text [previous,next], then click on button belong to direction
	 * to search for an icon name.
	 * @param direction
	 * @return
	 */
	public ResourceInfoPage clickPreviusNextIconPageBtn(String direction) {
		By iconButton = By.xpath("//button[@class='btn btn-primary btn-" + direction + "']");
		driver.findElement(iconButton).click();
		return new ResourceInfoPage();
	}
	
	/**
	 * [ML]This method click on found icon from selectResourceIcon method
	 * @return
	 */
	public ResourceInfoPage clickResourceIcon() {
		wait.until(ExpectedConditions.elementToBeClickable(resourceOpenIconBtn));
		resourceOpenIconBtn.click();
		return new ResourceInfoPage();
	}
	
	/**
	 * [ML]This method click on save button if a resource is created or edited.
	 * @return
	 */
	public ResourcesPage clickSaveResourceBtn() {
		saveResourceBtn.click();
		return new ResourcesPage();
	}
	
	/**
	 * [ML]Method that close resourceAsociationPage
	 * @return ResourcesPage
	 */
	public ResourcesPage clickCancelCloseWindowButton() {
		closeCancelBtn.click();
		return new ResourcesPage();		
	}
	
	/**
	 * [ML]Return the resource icon name from in resourceInfoPage if is present
	 * @param iconTitle
	 * @return boolean if is or not present
	 */
	public boolean getResourceIcon(String iconTitle) {
		By resourceIcon = By.xpath(".//*[@id='resourcesGrid']/descendant::*/span[@class='fa " +
				iconTitle + "']");
		return UIMethods.isElementPresent(resourceIcon);
	}
	
	/**
	 * [CG]This method click on cancel button.
	 * @return
	 */
	public ResourcesPage clickCancelResourceBtn() {
		cancelBtn.click();
		return new ResourcesPage();
	}
	
	/**
	 * [CG]This method cleans resource name field in resource info page
	 * @return
	 */
	public ResourceInfoPage clearResourceName() {
		wait.until(ExpectedConditions.visibilityOf(resourceNameTxtBox));
		resourceNameTxtBox.clear();
		return new ResourceInfoPage();
	}
	
	/**
	 * This method cleans resource display name field in resource info page
	 * @return
	 */
	public ResourceInfoPage clearResourceDisplayName() {
		wait.until(ExpectedConditions.visibilityOf(resourceDisplayNameTxtBox));
		resourceDisplayNameTxtBox.clear();
		return new ResourceInfoPage();
	}	
}
