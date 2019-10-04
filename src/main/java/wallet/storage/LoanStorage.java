package wallet.storage;

import wallet.model.record.Loan;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LoanStorage extends Storage<Loan> {
    public static final String DEFAULT_STORAGE_FILEPATH_LOAN = "./data/loan.txt";

    @Override
    public ArrayList<Loan> loadFile() {
        ArrayList<Loan> loanList = new ArrayList<>();
        boolean isLend;
        boolean isSettled;

        try {
            RandomAccessFile raf = new RandomAccessFile(DEFAULT_STORAGE_FILEPATH_LOAN, "r");
            String str;
            while (raf.getFilePointer() != raf.length()) {
                str = raf.readLine();
                String[] data = str.split(",");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                Loan loan = null;

                if (data[4].equals("0")) {
                    isLend = false;
                } else {
                    isLend = true;
                }
                if (data[5].equals("0")) {
                    isSettled = false;
                } else {
                    isSettled = true;
                }
                if (data.length == 6) {
                    loan = new Loan(data[1],LocalDate.parse(data[3], formatter), Double.parseDouble(data[2]),
                            isLend, isSettled);
                }

                if (loan != null) {
                    loan.setId(Integer.parseInt(data[0]));
                    loanList.add(loan);
                }
            }
            raf.close();
        } catch (FileNotFoundException e) {
            System.out.println("No saved loans found.");
        } catch (IOException e) {
            System.out.println("End of file.");
        }
        for( Loan l:loanList) {
            System.out.println(l.toString());
        }
        return loanList;
    }

    @Override
    public void writeToFile(Loan loan) {
        try {
            RandomAccessFile raf = new RandomAccessFile(DEFAULT_STORAGE_FILEPATH_LOAN, "rws");
            raf.seek(raf.length());
            if (raf.getFilePointer() != 0) {
                raf.writeBytes("\r\n");
            }
            raf.writeBytes(loan.writeToFile());
            raf.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    @Override
    public void updateToFile(Loan loan, int index) {

    }

    @Override
    public void removeFromFile(ArrayList<Loan> loanList, int index) {

    }
}
