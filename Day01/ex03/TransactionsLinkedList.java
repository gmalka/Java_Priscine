import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList{
    private int                length;
    private _Transactions_list first;
    private _Transactions_list last;

    public TransactionsLinkedList()
    {
        length = 0;
        first = null;
        last = null;
    }

    public TransactionsLinkedList(Transaction transaction)
    {
        length = 1;
        first = new _Transactions_list(transaction);
        last = first;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        if (first == null) {
            length++;
            first = new _Transactions_list(transaction);
            last = first;
            return ;
        }
        length++;
        last.next = new _Transactions_list(transaction);
        last.next.pre = last;
        last = last.next;
    }

    private void deleteThis(_Transactions_list counter) {
        if (length == 1) {
            first = last = null;
            length--;
            return;
        }
        else if (counter.pre == null)
        {
            first = counter.next;
            first.pre.next = null;
            first.pre = null;
            length--;
            return ;
        }
        else if (counter.next == null)
        {
            last = last.pre;
            last.next.pre = null;
            last.next = null;
            length--;
            return ;
        }
        counter.pre.next = counter.next;
        counter.pre.next.pre = counter.pre;
        counter.next = null;
        counter.pre = null;
        length--;
    }

    @Override
    public Transaction getTransactionById(UUID id) throws TransactionNotFoundException {
        _Transactions_list counter = first;
        while (counter != null)
        {
            if (counter.current.getId() == id)
            {
                return counter.current;
            }
            counter = counter.next;
        }
        throw new TransactionNotFoundException();
    }

    @Override
    public void removeTransaction(UUID id) throws TransactionNotFoundException {
        _Transactions_list counter = first;
        while (counter != null)
        {
            if (counter.current.getId() == id)
            {
                deleteThis(counter);
                return;
            }
            counter = counter.next;
        }
        throw new TransactionNotFoundException();
    }

    @Override
    public void removeTransaction(Transaction transaction) throws TransactionNotFoundException {
        _Transactions_list counter = first;
        while (counter != null)
        {
            if (counter.current == transaction)
            {
                deleteThis(counter);
                return ;
            }
            counter = counter.next;
        }
        throw new TransactionNotFoundException();
    }

    @Override
    public Transaction[] toArray() {
        if (length == 0)
            return null;
        Transaction[]       transactions = new Transaction[length];
        _Transactions_list  counter = first;
        int                 i = 0;
        while (counter != null)
        {
            transactions[i] = counter.current;
            counter = counter.next;
            i++;
        }
        return transactions;
    }

    private class _Transactions_list {
        public Transaction current;
        public _Transactions_list next;
        public _Transactions_list pre;

        public _Transactions_list()
        {
            current = null;
            next = null;
            pre = null;
        }

        public _Transactions_list(Transaction transaction)
        {
            current = transaction;
            next = null;
            pre = null;
        }
    }
}
