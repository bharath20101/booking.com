Feature: Search properties

@Regression
Scenario: Search properties in Stockholm

Given User has launched Booking.com website
When User selectes Stockholm
And User sorts properties by top reviewed category
And selects third property from the top
And selects any room and clicks reserve button
And comesback to search results page
And selects fourth preperty from the top
And selects any room and clicks reserve button for the foruth result