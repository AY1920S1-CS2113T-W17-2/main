package wallet.logic.command;

import wallet.model.Wallet;
import wallet.model.contact.Contact;
import wallet.model.record.Expense;
import wallet.model.record.Loan;
import wallet.ui.Ui;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * The ListCommand Class handles all list commands.
 */
public class ListCommand extends Command {
    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_LIST_CONTACTS = "Here are the contacts in your list:";
    public static final String MESSAGE_LIST_EXPENSES = "Here are the expenses in your list:";
    public static final String MESSAGE_LIST_NO_EXPENSES = "There are no expenses for ";
    public static final String MESSAGE_LIST_NO_LOANS = "There are no loans for ";
    public static final String MESSAGE_LIST_RECURRING_EXPENSES = "Here are the recurring expenses in your list:";
    public static final String MESSAGE_LIST_LOANS = "Here are the loans in your list:";
    public static final String MESSAGE_USAGE = "Error in format for command."
            + "\nExample: " + COMMAND_WORD + " all"
            + "\nExample: " + COMMAND_WORD + " expense"
            + "\nExample: " + COMMAND_WORD + " loan"
            + "\nExample: " + COMMAND_WORD + " task"
            + "\nExample: " + COMMAND_WORD + " recurring";

    private final String record;

    /**
     * Constructs a ListCommand object.
     *
     * @param record The Record object.
     */
    public ListCommand(String record) {
        this.record = record;
    }

    /**
     * Lists the Record objects in any list and returns false.
     *
     * @param wallet The Wallet object.
     * @return False.
     */
    @Override
    public boolean execute(Wallet wallet) {
        boolean isListAll = false;

        switch (record) {
        case "recurring":
            System.out.println(MESSAGE_LIST_RECURRING_EXPENSES);
            ArrayList<Expense> recList = wallet.getExpenseList().getRecurringExpense();
            Ui.printExpenseTable(recList);
            break;

        case "all":
            isListAll = true;
            //fallthrough

        case "contact":
            //@@author Xdecosee
            System.out.println(MESSAGE_LIST_CONTACTS);
            ArrayList<Contact> contactList = wallet.getContactList().getContactList();
            Ui.printContactTable(contactList);
            if (!isListAll) {
                break;
            }
            //@@author
            //fallthrough

        case "loan":
            //@@author A0171206R
            ArrayList<Loan> loanList = wallet.getLoanList().getLoanList();
            Ui.printLoanTable(loanList);
            if (!isListAll) {
                break;
            }
            //@@author
            //fallthrough

        case "expense":
            ArrayList<Expense> expenseList = wallet.getExpenseList().getExpenseList();
            System.out.println(MESSAGE_LIST_EXPENSES);
            Ui.printExpenseTable(expenseList);
            if (!isListAll) {
                break;
            }
            //fallthrough

        default:
            //@@author matthewng1996
            if (!isListAll) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(record.trim(), formatter);

                ArrayList<Expense> expensesList = new ArrayList<Expense>();
                ArrayList<Loan> loansList = new ArrayList<>();

                if (date != null) {
                    if (wallet.getExpenseList().getExpenseList().size() != 0
                            || wallet.getLoanList().getLoanList().size() != 0) {
                        if (wallet.getExpenseList().getExpenseList().size() != 0) {
                            for (Expense e : wallet.getExpenseList().getExpenseList()) {
                                if (e.getDate().equals(date)) {
                                    expensesList.add(e);
                                }
                            }

                            if (expensesList.size() != 0) {
                                Ui.printExpenseTable(expensesList);
                            } else {
                                System.out.println(MESSAGE_LIST_NO_EXPENSES
                                        + date.getDayOfMonth() + " "
                                        + new DateFormatSymbols().getMonths()[date.getMonthValue() - 1]
                                        + " " + date.getYear());
                            }
                        }
                        //@@author A0171206R
                        if (wallet.getLoanList().getLoanList().size() != 0) {
                            for (Loan l : wallet.getLoanList().getLoanList()) {
                                if (l.getDate().equals(date)) {
                                    loansList.add(l);
                                }
                            }

                            if (loansList.size() != 0) {
                                Ui.printLoanTable(loansList);
                            } else {
                                System.out.println(MESSAGE_LIST_NO_LOANS
                                        + date.getDayOfMonth() + " "
                                        + new DateFormatSymbols().getMonths()[date.getMonthValue() - 1]
                                        + " " + date.getYear());
                            }
                        }
                        //@@author
                    }
                } else {
                    System.out.println(MESSAGE_USAGE);
                }
                break;
                //@@author
            }
        }
        return false;
    }
}
