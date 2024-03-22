*** Settings ***
Documentation   Test för infotivs car rental
Library    SeleniumLibrary
#Suite Setup    Open Browser To Start

*** Variables ***
${url}     http://rental8.infotiv.net/
${browser}    Chrome
${user}    parers
${password}    Password1234
${name}    par
${surname}    ers
${number}    0701234562
${email}    blub1@gmail.se
${startDate}    323
${endDate}    323
${cardNumber}    1234567890987654
${cardHolder}    Pär Ershag
${cvc}    476

*** Test Cases ***
Create user
    [Documentation]    Creates a user
    [Tags]    Test to create a user
    [Setup]    Open Browser To Start
    Click Button    //button[@id='createUser']
    Input Text    //input[@id='name']    ${name}
    Input Text    //input[@id='last']    ${surname}
    Input Text    //input[@id='phone']    0701234221
    Input Text    //input[@id='emailCreate']    email341@gmail.se
    Input Text    //input[@id='confirmEmail']    email341@gmail.se
    Input Text    //input[@id='passwordCreate']    passwordets
    Input Text    //input[@id='confirmPassword']    passwordets
    Click Button    //button[@id='create']
    Wait Until Page Contains Element    //button[@id='mypage']
    Click Button    //button[@id='logout']
    Close Browser

Using Wrong format for email when creating user
    [Documentation]    Using the wrong format for email slot
    [Tags]    Test to see that you can't use a non email format as email
    [Setup]    Open Browser To Start
    Click Button    //button[@id='createUser']
    Input Text    //input[@id='name']    39485
    Input Text    //input[@id='last']    5932854
    Input Text    //input[@id='phone']    0707123456
    Input Text    //input[@id='emailCreate']    jklasd@.se
    Input Text    //input[@id='confirmEmail']    jklasd@.se
    Input Text    //input[@id='passwordCreate']    ${password}
    Input Text    //input[@id='confirmPassword']    ${password}
    Click Button    //button[@id='create']
    Wait Until Page Does Not Contain Element    //button[@id='logout']
    Close Browser

Sort by car and book
    [Documentation]    Tries to sort cars by Volvo and book the first car
    [Tags]    Test will confirm that no book button appears
    [Setup]    Open Browser To Start
    User Have Logged In    ${url}    ${email}    ${password}
    Input Text    //input[@id='start']    ${startDate}
    Input Text    //input[@id='end']    ${endDate}
    Click Button    //button[@id='continue']
    Click Button    //div[@id='ms-list-1']//button[@type='button']
    Click Element    //label[normalize-space()='Volvo']
    Wait Until Page Does Not Contain Element    //tbody/tr[1]/td[5]/form[1]/input[4]
    Click Button    //button[@id='logout']
    Close Browser

Logging in with wrong password
    [Documentation]    Opens browser and tries to login using wrong password
    [Tags]    Test to see if the error message saying wrong password
    [Setup]    Open Browser To Start
    Input Text    //input[@id='email']    ${email}
    Input Text    //input[@id='password']    password
    Click Button    //button[@id='login']
    Wait Until Page Contains Element    //label[@id='signInError']
    Close Browser

See booking history
    [Documentation]    Logs in and checks booking history
    [Tags]    Test to see if booking history is visible
    [Setup]    Open Browser To Start
    User Have Logged In    ${url}    ${email}    ${password}
    Click Button    //button[@id='mypage']
    Page Should Contain Element    //td[@id='order4']
    Click Button    //button[@id='show']
    Wait Until Page Contains Element    //div[@id='orderHistory']//div//h1[@id='historyText']
    Click Button    //button[@id='logout']
    Close Browser


Rent a car
    [Tags]    VG_test
    Given User have logged in    ${url}    ${email}    ${password}
    When User selects date and car    ${startDate}    ${endDate}
    And User selects payment option    ${cardNumber}    ${cardHolder}    ${cvc}
    Then Booking should be visible on My page


*** Keywords ***
Open browser to start
    Open browser    browser=chrome
    Go to    ${url}

User have logged in
    [Documentation]    Opens browser and navigates to website and logs in
    [Tags]    Test for logging in to car rental
    [Arguments]    ${url}    ${email}    ${password}
    [Setup]    Open Browser To Start
    Input Text    //input[@id='email']    ${email}
    Input Text    //input[@id='password']    ${password}
    Click Button    //button[@id='login']
    
User selects date and car
    [Documentation]    User selects the dates of the rental and the car to rent
    [Tags]    Test for selecting date and car
    [Arguments]    ${startDate}    ${endDate}
    Input Text    //input[@id='start']    ${startDate}
    Input Text    //input[@id='end']    ${endDate}
    Click Button    //button[@id='continue']
    Click Element    //tbody/tr[3]/td[5]/form[1]/input[4]

User selects payment option
    [Documentation]    User fills out card information
    [Tags]    Test for choosing payment
    [Arguments]    ${cardNumber}    ${cardHolder}    ${cvc}
    Input Text    //input[@id='cardNum']    ${cardNumber}
    Input Text    //input[@id='fullName']    ${cardHolder}
    Input Text    //input[@id='cvc']    ${cvc}
    Select From List By Index    //select[@title='Month']    8
    Select From List By Index    //select[@title='Year']    6
    Click Button    //button[@id='confirm']

Booking should be visible on My page
    [Documentation]    Navigates to My page and confirms the booking went through
    [Tags]    Validating the booking of the car
    Click Button    //button[@id='mypage']
    Wait Until Page Contains Element    //button[@id='unBook1']
    Click Button    //button[@id='logout']
    Close Browser
