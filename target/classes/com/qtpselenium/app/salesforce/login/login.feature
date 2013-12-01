Feature: Logging into Salesforce

In order to work 
As a sales person
I want to login

@login
Scenario Outline: Logging in salesforce

Given Runmode is "<Runmode>"
Given I go to "loginURL" on "<Browser>"
And I enter "loginusername" as "<Username>"
And I enter "loginpassword" as "<Password>"
And I click on "loginButton"
Then Login should be "<Expected_Result>"

 Examples:
|Runmode  | Browser    | Username            			| Password 	   | Expected_Result |
|  N      | Mozilla    | xxxxx   			 			| zxxxx        | failure         |
|  N      | Mozilla    | shartul.kumar@aol.com          | pass@1234    | success         |
|  N      | Chrome     | xxxxx    						| zxxxx        | failure         |
|  N      | Chrome     | shartul.kumar@aol.com          | pass@1234    | success         |
|  Y      | IE         | shartul.kumar@aol.com			| pass@1234	   | success	     | 
 