USE `Book_Store`;

DELIMITER $$

DROP TRIGGER IF EXISTS Book_Store.Copies_Check$$
USE `Book_Store`$$
CREATE DEFINER = CURRENT_USER TRIGGER `Book_Store`.`Copies_Check` BEFORE UPDATE ON `Book` FOR EACH ROW
BEGIN

if NEW.Copies < 0 then
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT = 'Available number of copies is not sufficient !';
end if;

END
$$
DELIMITER ;