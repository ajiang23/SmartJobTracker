# **Project Proposal - SmartJobTracker**

## **What does it do?**
The **SmartJobTracker** application helps users track job applications, update their statuses, and gain insights from historical data. By streamlining the job search process, it ensures users stay organized and proactive. 

## **Who will use it?**
This application is designed for **students and professionals** who need a structured and efficient way to manage their job search.  

## **Why does it matter?**
Tracking job applications can be overwhelming. Many job seekers tend to rely on confirmation emails or Word/Excel documents, but these methods lack **consistency, automation, and intelligence**. 

**SmartJobTracker** eliminates these inefficiencies by providing a **centralized, time-sensitive, and intelligent tracking system** to help users manage their applications effectively.  

## **User Stories**
- As a user, I want to be able to add a new job application to my job tracking list.  
- As a user, I want to be able to view all job applications in a list and filter them based on specific criteria.  
- As a user, I want to be able to update the status of a job application as my application progresses.  
- As a user, I want to be able to delete a job application that I no longer want to track. 
- As a user, I want to be able to save the state of the application anytime. When I select the exit option from the application menu, I want to be reminded to save my job application list to file and have the option to do so or not. 
- As a user, I want to be able to load my job application list from file anytime. When I start the application, I want to be given the option to load my job application list from file. 

## **Instructions for End User**
- You can generate the first required action related to the user story #1 "adding multiple job applications to my job tracking list" by clicking the "Add job" button at the top menu bar. A form will appear where you must fill in all the required information. Click "OK", and the newly added job application will immediately appear at the bottom of the job tracking list. 
- You can generate the second required action related to the user story #2 "viewing all job applications in a list and filtering them based on 
specific criteria" by clicking "Filter by: None" at the top menu bar. In the dropdown menu, select "Filter by: Company Name." A pop-up window will appear, prompting you to enter the company name you want to filter by. After entering the company name, click "OK", and the list will update to display only job applications associated with the specified company. To remove the filter and view all applications again, click "Filter by: Company Name", then select "Filter by: None" from the dropdown menu.
- You can delete an existing job application by selecting one from the list, then choose "Delete Selected" at bottom left. 
- You can locate my visual component which is a pie chart visualizing the percentage of job applications based on their current status by clicking "Menu" at the top left corner, then selecting "View Status Breakdown" from the dropdown menu. A new window will open displaying the pie chart. 
- You can save the current state of the application at any time by selecting "Save Applications" from the "Menu" dropdown in the top left corner. You will also be prompted to choose to save the state of the application when exiting by choosing "Exit" from the "Menu."
- You can reload your previously saved job applications at any time by selecting "Load Applications" from the "Menu" dropdown. You will also be prompted to choose whether to load your previously saved data when first opening the application. Please note, if you choose not to load the previous state of the application and later choose to save the state of the current session, the new data will overwrite the previous session's data, and the old data will be lost. 

## **Phase 4: Task 2**
```
Event Log:
Fri Mar 28 21:47:27 PDT 2025 
New job application added: specialist in abc
Fri Mar 28 21:47:36 PDT 2025
Updated status to: First_interview for specialist in abc
Fri Mar 28 21:47:42 PDT 2025
Filtered by company name: abc
Fri Mar 28 21:47:46 PDT 2025
Job application removed: specialist in abc
```