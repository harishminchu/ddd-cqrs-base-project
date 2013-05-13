package com.librarymanagement.domain;

import com.autobizlogic.abl.annotations.CommitConstraint;
import com.autobizlogic.abl.annotations.Constraint;
import com.autobizlogic.abl.annotations.CurrentBean;

/**
 * Created by IntelliJ IDEA.
 * User: Nthdimenzion
 * Date: 31/1/13
 * Time: 11:32 AM
 */
public class BookLendingLogic extends LogicObject{

    @CurrentBean
    private BookLending bookLending;

/*
    @Constraint(value = "book.availableCopies > 3",
                errorMessage = "Need to have atleast 3 copies of {book.name} in the library")
*/
    @CommitConstraint(value = "book.availableCopies > 3",
        errorMessage = "Need to have atleast 3 copies of {book.name} in the library")

public void constraintOnAvailableBooksPriorToLending(){}
}
