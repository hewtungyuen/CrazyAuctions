## Overview

Software system that facilitates price ascending bid process, supporting administrative and customer-centric business processes. Consists of command-line clients connected to a common backend. 

## Tech stack
* Jakarta Enterprise Beans (EJB)
* Java Persistence API (JPA)
* MySQL

## Features 

#### Admin 
* Creation of credit package 
* Creation of auction listings with opening and closing date 

#### Customer
* Purchase of credit packages 
* Placement of bids using purchased credits 
* Visibility over credit transactions (credit package purchases, bid placement, refunds for outbid items)

#### Premium Customer
* Bid sniping: specifying duration before auction listing end date to exercise a one-time bid  

## Implementation 
#### JPA
Utilised JPA as object relational mapping framework. Modelled entity classes as shown below.

![image](https://user-images.githubusercontent.com/63499355/234935920-3c3013aa-1072-4902-89a8-b02e8c21cddd.png)

#### EJB
Utilised session beans which are named after entities. Each session bean handles business operations related to its entity.  Also utilised programmatic timers for bid sniping, and to begin / terminate bidding process for auction listings.
