USE `Book_Store`;

DELIMITER $$

DROP TRIGGER IF EXISTS Book_Store.Threshold_Check$$
USE `Book_Store`$$
CREATE DEFINER = CURRENT_USER TRIGGER `Book_Store`.`Threshold_Check` AFTER UPDATE ON `Book` FOR EACH ROW
BEGIN

declare difference int;
declare testISBN VARCHAR(15);
set testISBN = '';
if NEW.Copies < NEW.Threshold then
	set difference = NEW.Threshold - NEW.Copies;
    select ISBN into testISBN from Book_Order where ISBN = NEW.ISBN;
    if (testISBN = '') then
		insert into Book_Order values (NEW.ISBN, difference, False);
	else
		update Book_Order set Copies = Copies + difference where ISBN = NEW.ISBN;
	end if;
end if;

END
$$
DELIMITER ;