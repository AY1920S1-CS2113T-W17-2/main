package wallet.logic.command;

import wallet.logic.LogicManager;
import wallet.model.Wallet;
import wallet.model.contact.Contact;
import wallet.model.record.Expense;
import wallet.model.record.Loan;
import wallet.ui.Ui;

/**
 * DeleteCommand class handles any command that involves deletion of Record objects.
 */
public class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_USAGE = "Format for delete command:"
            + "\ndelete expense <id>"
            + "\nExample: " + COMMAND_WORD + " expense 2";
    public static final String MESSAGE_SUCCESS_DELETE_EXPENSE = "Noted. I've removed this expense:";
    public static final String MESSAGE_SUCCESS_DELETE_LOAN = "Noted. I've removed this loan:";
    public static final String MESSAGE_SUCCESS_DELETE_CONTACT = "Noted. I've removed this contact:";
    public static final String MESSAGE_ERROR_DELETE_EXPENSE = "An error occurred when trying to delete expense.";
    public static final String MESSAGE_ERROR_DELETE_LOAN = "An error occurred when trying to delete loan.";
    public static final String MESSAGE_ERROR_DELETE_CONTACT = "An error occurred when trying to delete contact.";
    public static final String MESSAGE_ERROR_LOAN_USAGE = "There are loans using this contact. Unable to delete!";
    private String object;
    private int id;

    /**
     * Constructs a DeleteCommand object.
     *
     * @param object A String object for deletion.
     * @param id     The id of the object in a specified list.
     */
    public DeleteCommand(String object, int id) {
        this.object = object;
        this.id = id;
    }

    /**
     * Deletes the specific Record object by entry id
     * and returns false.
     *
     * @param wallet The Wallet object.
     * @return A boolean which indicates whether program terminates.
     */
    @Override
    public boolean execute(Wallet wallet) {
        switch (object) {
        case "expense":
            //@@author kyang96
            Expense expense = wallet.getExpenseList().deleteExpense(id);
            if (expense != null) {
                wallet.getExpenseList().setModified(true);
                System.out.println(MESSAGE_SUCCESS_DELETE_EXPENSE);
                Ui.printExpense(expense);
            } else {
                Ui.printError(MESSAGE_ERROR_DELETE_EXPENSE);
            }
            break;
        //@@author
        case "loan":
            //@@author A0171206R
            Loan loan = wallet.getLoanList().deleteLoan(id);
            if (loan != null) {
                wallet.getLoanList().setModified(true);
                System.out.println(MESSAGE_SUCCESS_DELETE_LOAN);
                Ui.printLoanTableHeaders();
                Ui.printLoanRow(loan);
                Ui.printLoanTableClose();
                if (!LogicManager.getWalletList().getWalletList().get(LogicManager.getWalletList().getState())
                        .getLoanList().checkUnsettledLoan()) {
                    LogicManager.getReminder().autoRemindStop();
                    System.out.println("Turning off auto reminders because all loans have been settled!");
                }
            } else {
                Ui.printError(MESSAGE_ERROR_DELETE_LOAN);
            }
            break;
        //@@author

        case "contact":
            //@@author Xdecosee
            for (Loan l : wallet.getLoanList().getLoanList()) {
                if (l.getPerson().getId() == id) {
                    Ui.printError(MESSAGE_ERROR_LOAN_USAGE);
                    return false;
                }
            }

            Contact contact = wallet.getContactList().deleteContact(id);
            if (contact != null) {
                wallet.getContactList().setModified(true);
                System.out.println(MESSAGE_SUCCESS_DELETE_CONTACT);
                Ui.printContact(contact);
            } else {
                Ui.printError(MESSAGE_ERROR_DELETE_CONTACT);
            }
            break;
            //@@author

        default:
            System.out.println(MESSAGE_USAGE);
            break;
        }
        return false;
    }
}
