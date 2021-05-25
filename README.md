# EmailApiPoc
Execute below steps for exposing API end points

# Step 1
Run below maven command for project directory</br>

<b>mvn clean package</b></br>

please find generated docs of API end point at \target\generated-docs\index.html.</br>

please find generated test coverage at \target\site\jacoco\index.html</br>

# Step 2 

Configure AWS Cli 


# Step 3
Create the AWS CloudFormation template with below command</br>

$ aws cloudformation package --template-file sam.yaml --output-template-file output-sam.yaml --s3-bucket mybucket

# Step 4 
Deploy newly created template with:

$ aws cloudformation deploy --template-file output-sam.yaml --stack-name mailApi --capabilities CAPABILITY_IAM

#Step 5
  
 Please find the end pointurl deployed at AWS by logging into the console </br>

Lambda < Application < Mail API    : copy the end point </br>
End point deployed by Vikas is : https://iht9pd610i.execute-api.us-east-2.amazonaws.com/Prod


# Step 6

Enable CORS setting for this end point from AWS api get way.

# Step 7
   
  Check First API List Emails API by below steps </br>
  a. Create new postman collection and new request type of POST .<br>
  b. Pass username and password as request params for list emails api <br>
  https://iht9pd610i.execute-api.us-east-2.amazonaws.com/Prod/inbox/emails?username=pieeyecandidate@outlook.com&password=2021-codder%23</br>
  c. please find resonse in body
  
# Step 8 

  Check Second List Particular email by passing message number as a parth variable .</br>
  
   a. Create new postman collection and new request type of POST .<br>
  b. Pass username and password as request params for list emails api <br>
  https://iht9pd610i.execute-api.us-east-2.amazonaws.com/Prod/inbox/email/2?username=pieeyecandidate@outlook.com&password=2021-codder%23 </br>
  c. please find resonse in body

#  Step 9

   Please find sample ui at below location </br>
   https://emailapiclient.s3.us-east-2.amazonaws.com/index.html
   
# Step 10 

   Please install CORS plugin in browser and turn it on ( it is workaround , as facing some issue related to CORS from S3)
   
# Step 11 

   1. enter username and password value , it will list all emails , if we want to open a particular email we can do that by clicking ViewMail button.
   2. If we want to show a particular email from incoming number basis we can check that by passing message number as well.   
