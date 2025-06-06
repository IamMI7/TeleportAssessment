# Setup Instructions
1. Prerequisites
- Java 21 installed (Using Java 21 instead of the latest 24 because spring only supports up to 21)
- Maven installed
- Git installed
- IntelliJ Installed

2. Git Clone
- Find a directory you want to clone the repository (e.g. Desktop), right click (further click show more options if on Windows 11) and click *open git bash here*
- Input the following line without quotations in the git bash terminal "git clone https://github.com/IamMI7/TeleportAssessment.git"

3. Run locally
- Using IntelliJ open the cloned repository
- Once opened go to and right click the src/main/TelepostAssessment file and click "Run 'TeleportAssessment.main()'

4. Test API Remotely
- Run this API (https://teleport-assessment-266097c5ed10.herokuapp.com/next-tracking-number?origin_country_id=MY&destination_country_id=ID&weight=1.234&created_at=2018-11-20T19:29:32%2B08:00&customer_id=de619854-b59b-425e-9db4-943979e1bd49&customer_name=RedBox%20Logistics&customer_slug=redbox-logistics) anyway you prefer
- Option 1: Open your prefered browser and in the search bar search for the above API
- Option 2: Run with a dedicated API Client like postman
- Edit the values of the request parameters to generate different responses.
- *Keep in mind tracking number will only be different when you change to a different CustomerID + CreatedAt combination as the tracking number is generated based on the 2 values composited together
- if you enter the same CustomerID + CreatedAt combination the tracking number will always be the same due to indempotency han

5. Test API Locally
- Once running successfully in the terminal of intelliJ.
- Run this API (http://localhost:8080/next-tracking-number?origin_country_id=MY&destination_country_id=ID&weight=1.234&created_at=2018-11-20T19:29:32+08:00&customer_id=de619854-b59b-425e-9db4-943979e1bd49&customer_name=RedBox Logistics&customer_slug=redbox-logistics) anyway you prefer
- Option 1: Open your prefered browser and in the search bar search for the above API
- Option 2: Run with a dedicated API Client like postman
- Edit the values of the request parameters to generate different responses.
- *Keep in mind tracking number will only be different when you change to a different CustomerID + CreatedAt combination as the tracking number is generated based on the 2 values composited together
- if you enter the same CustomerID + CreatedAt combination the tracking number will always be the same due to indempotency handling*

# Implementation considerations:
○ Generated Tracking number matches the regex pattern: ^[A-Z0-9]{1,16}$.
- The sha256ToBase36() method ensures that the tracking number only includes [A-Z0-9] characters and is exactly 16 characters long and all uppercase *I avoided using a regex pattern directly to validate the output of the method because it will just introduce innecessary complexity and reduce the performance of the API.*
○ Ensure Uniqueness of tracking number
- Tracking number is generated using a combination of the customerId and CreatedAt timestamp to ensure uniqueness if new orders are being placed.
- If CustomerId and CreatedAt parameter values are the exact same in multiple requests the tracking number will be the same but in my opinion this is to be able to handle indempotency cases.
- For Example, If the client calls the API multiple times with the exact same CustomerId and CreatedAt we can assume this client is retrying to place the same order over and over again.
○ Efficiency
- SHA-256 hashing is efficient and non-blocking.
- No database or I/O overhead
○ Concurrency
- Your code is stateless, thread-safe, and doesn't share mutable data.
○ Scalability
- Service is stateless, it's horizontally scalable by nature (can run multiple instances).
- No shared state or DB locking.
○ Creativity
- Using customerId + createdAt as the source for deterministic tracking numbers is clean and logical. *Based on what we have. In a better world passing in an Order Specific UUID is a better approach than using CustomerID UUID*
