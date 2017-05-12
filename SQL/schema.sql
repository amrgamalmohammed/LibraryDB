drop schema if exists book_store;
create schema book_store;
use book_store;

create table Book (
	ISBN		varchar(15) PRIMARY key,
    Title		varchar(40) not null,
    Pub_name	varchar(40) not null,
    Pub_year	year,
    Price		double not null,
    Cat_id		int not null,
    Copies		int not null,
    Threshold	int not null);
    
create table Book_Authors (
	ISBN		varchar(15) not null,
    Author_name	varchar(40) not null,
    constraint pk primary key (ISBN, Author_name));
    
create table Publisher (
	Pub_name	varchar(40) primary key,
    Address		varchar(40) not null,
    Phone		varchar(15));
    
create table Book_Order (
	ISBN		varchar(15) primary key,
    Copies		int not null,
    Accepted	boolean not null);
    
create table Library_User (
	F_Name		varchar(40) not null,
    L_Name		varchar(40) not null,
    Username	varchar(40)	primary key,
    User_Password	varchar(40) not null,
    Email		varchar(40) not null unique,
    Phone		varchar(15),
    Address		varchar(40),
    Is_Manager	boolean not null);
    
create table Category (
	Cat_Name	varchar(40) not null unique,
    Cat_Id			int primary key);
    
create table Sales (
	Username	varchar(40) not null,
    ISBN		varchar(15) not null,
    Copies		int not null,
    Sell_Date	date not null,
    Price		double not null,
    constraint pk primary key (Username, ISBN, Sell_Date));
    
alter table Book_Authors add constraint fk1 foreign key (ISBN) references Book(ISBN) ON UPDATE CASCADE;
alter table Book add constraint fk2 foreign key (Pub_Name) references Publisher(Pub_Name) ON UPDATE CASCADE;
alter table Book add constraint fk3 foreign key (Cat_id) references Category(Cat_Id) ON UPDATE CASCADE;
alter table Book_Order add constraint fk4 foreign key (ISBN) references Book(ISBN) ON UPDATE CASCADE;
alter table Sales add constraint fk5 foreign key (ISBN) references Book(ISBN) ON UPDATE CASCADE;
alter table Sales add constraint fk6 foreign key (Username) references Library_User(Username) ON UPDATE CASCADE;

INSERT INTO Library_User VALUES ('Amr', 'Gamal', 'amrgamal', '1234', 'amrgamal@hotmail.com', '01121183380', '10 abu keer st.', true);