USE `Book_Store`;

DELIMITER $$

DROP TRIGGER IF EXISTS Book_Store.Modefied_Copies$$
USE `Book_Store`$$
CREATE DEFINER = CURRENT_USER TRIGGER `Book_Store`.`Modefied_Copies` BEFORE DELETE ON `Book_Order` FOR EACH ROW
BEGIN

if OLD.Accepted then
	update book as b
    set b.Copies = b.Copies + OLD.Copies
    where b.ISBN = OLD.ISBN;
end if;

END
$$
DELIMITER ;