package com.librarymanagement.application.commands.handlers;

import com.librarymanagement.application.commands.*;
import com.librarymanagement.domain.*;
import com.librarymanagement.domain.error.NotEnoughCopiesException;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.nthdimenzion.cqrs.command.AbstractCommandHandler;
import org.nthdimenzion.cqrs.command.annotations.CommandHandler;
import org.nthdimenzion.crud.ICrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@CommandHandler
public class BookCommandHandler extends AbstractCommandHandler {

    @Autowired
    private BookBuilder bookBuilder;

    @Autowired
    private IBookRepository bookRepository;

    @Autowired
    private ICrud crudDao;

    @Transactional
    public Long registerBook(RegisterBookCommand purchaseBookCommand) {
        Book book = bookBuilder.createBook(purchaseBookCommand.name, purchaseBookCommand.isbn, purchaseBookCommand.cost).withAuthors(purchaseBookCommand.authors).purchaseCopies(purchaseBookCommand.copies).build();
        Long bookId = bookRepository.registerBook(book);
        return bookId;
    }

    @Transactional
    public Book updateBook(UpdateBookCommand updateBookCommand) {
        Book book = bookRepository.getBookFromId(updateBookCommand.id);
        book = populate(updateBookCommand,book);
        book.setCost(Money.of(CurrencyUnit.AUD,10));
        book = bookRepository.updateBook(book);
        System.out.println("book.getTotalForSudarshanCopies() -- > " + book.getTotalForSudarshanCopies());
        return book;
    }

    @Transactional
    public Boolean issueBook(IssueBooksCommand bookIssueCommand) throws NotEnoughCopiesException {
        Set<BookId> bookIds = bookIssueCommand.bookIds;
        Member member = crudDao.find(Member.class, bookIssueCommand.memberId);
        for (BookId bookId : bookIds) {
            Book book = bookRepository.geBookWithUid(bookId);
            book.lend(member.getId());
            bookRepository.lend(book);
        }
        return success;
    }

    @Transactional
    public Boolean returnBook(ReturnBooksCommand bookReturnCommand) {
        Set<BookId> bookIds = bookReturnCommand.bookIds;
        Member member = crudDao.find(Member.class, bookReturnCommand.memberId);
        for (BookId bookId : bookIds) {
            Book book = bookRepository.geBookWithUid(bookId);
            book.rentalExpiry(member.getId());
            bookRepository.rentalExpiry(book);
        }
        return success;
    }

    @Transactional
    public Book purchaseCopies(PurchaseBookCopiesCommand purchaseBookCopiesCommand){
        Book book = bookRepository.geBookWithUid(purchaseBookCopiesCommand.bookId);
        book.purchaseCopies(purchaseBookCopiesCommand.noOfCopiesToPurchase);
        return bookRepository.purchaseCopies(book);
    }


    @Transactional
    public Book sellCopies(SellBookCopiesCommand sellBookCopiesCommand) throws NotEnoughCopiesException {
        Book book = bookRepository.geBookWithUid(sellBookCopiesCommand.bookId);
        book.sellCopies(sellBookCopiesCommand.noOfCopiesToSell);
        return bookRepository.sellCopies(book);
    }

}
