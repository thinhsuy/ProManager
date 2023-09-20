# ProManager - Projects Manager EcoSystem
## Introduction
ProManager is a versatile and comprehensive project management solution designed to empower users in effectively overseeing projects, simplifying intricate processes, and staying well-informed through proactive notifications. This innovative platform has been meticulously crafted to meet the diverse and evolving needs of project managers and teams across various industries.

At the heart of ProManager is its user-friendly interface, which streamlines project creation, organization, and tracking. Users can effortlessly set project objectives, define tasks, allocate resources, and establish precise project timelines.

## Android Studio EDK

Android Studio is the primary integrated development environment (IDE) used by developers to create Android applications. It provides a robust set of tools and techniques for designing, developing, and testing Android apps. Two fundamental technologies in Android Studio are XML and Java:

- XML (Extensible Markup Language):
  - Layout Design: XML is extensively used for defining the layout of Android app interfaces. Developers use XML files to design the user interface (UI) elements, such as buttons, text views, and input fields, by specifying their attributes, dimensions, and positions.
  - Resource Management: XML is crucial for managing app resources like strings, colors, and dimensions. Resource files, stored in XML format, help maintain consistency and simplify localization efforts by separating content from code.
    
- Java (or Kotlin):
  - Activity and Fragment Management: Java is essential for managing activities (individual screens or windows) and fragments (reusable UI components) within an Android app.
  - Integration with Android APIs: Developers use Java (or Kotlin) to interact with Android's extensive libraries and APIs, accessing device features like sensors, GPS, camera, and more.

![image](https://github.com/thinhsuy/ProManager_Android_App/assets/81562297/abf44227-7582-40ce-8daf-505b783ceff8)


## Architecture Design
### Logic View

<img width="50%" alt="Screen Shot 2023-09-20 at 20 17 50" src="https://github.com/thinhsuy/ProManager_Android_App/assets/81562297/2e27b2e4-4621-4dac-9248-42c02532947b">

The diagram of Logic View represents a typical three-tier software architecture, which is divided into three layers: 

1. **Presentation Layer**: This is the topmost layer that users interact with directly. It consists of:
   - **Presentation Logic**: This component handles the logic for the user interface (UI). It determines how data is displayed to the user.
   - **User Interface Management**: This component manages the user interface, including elements like buttons, menus, and other controls.

2. **Business Layer**: This is the middle layer that processes user interactions and makes logical decisions. It includes:
   - **Account Management**: This component manages user accounts, including login credentials and user profiles.
   - **Notification Management**: This component handles notifications sent to users.
   - **Task Management**: This component manages tasks within the application.

3. **Data Management Layer**: This is the bottom layer that interacts with databases or other data sources. It includes:
   - **Account Data**: This component stores and retrieves data related to user accounts.
   - **User Data**: This component stores and retrieves general data related to users.
   - **Task Data**: This component stores and retrieves data related to tasks.

The arrows in the diagram represent interactions between these components. For example, when a user logs in, the Presentation Layer interacts with the Business Layer's Account Management component, which in turn interacts with the Data Management Layer's Account Data component to verify the user's credentials.

### Usercase Workflows
![Untitled Diagram-FolderFlows](https://github.com/thinhsuy/ProManager_Android_App/assets/81562297/ff7b1bd8-d4a0-4830-9664-f758e09a01a8)

The image of usecase workflow shared represents a use case diagram for a project management application and here's a breakdown of the process:

1. **User Registration**: The first step for any new user is to register in the application. This involves providing necessary details like name, email, and password.

2. **Login**: Once registered, users can log into the application using their credentials.

3. **Project Creation**: After logging in, users can create new projects. Each project requires certain details like project name, description, start date, end date, and team members.

4. **Task Assignment**: Within each project, users can create and assign tasks to team members. Each task will have details like task name, description, assignee, due date, and status.

5. **Task Update**: Users can update the status of tasks as they progress. This could involve changing the status from 'To Do' to 'In Progress' to 'Done'.

6. **Notification**: The application sends notifications to users about important events like task assignment, task updates, and project updates.

7. **Reporting**: The application provides reports on project progress and individual performance.

Remember that this is a high-level overview and the actual process might have more steps depending on the specific features of the application.

# Contribution
@NguyenDuyThinh
@VuDucDung
@NguyenNhatQuynh
@LeMinhNhat
