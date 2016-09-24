package com.godspeed.service.dbflow;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.io.Serializable;

public abstract class BaseTableModel extends BaseModel implements Serializable {



    @Override
    @JsonBackReference
    public ModelAdapter getModelAdapter() {
        return super.getModelAdapter();
    }


    public static   void dropAll(Class<BaseModel> clazz){
        new Delete().from(clazz).execute();
    }


    // run a transaction synchronous easily.
    public static void executeSyncTransaction(ITransaction transaction){
        DatabaseDefinition database = FlowManager.getDatabase(GodspeedDatabase.databaseClass);
        database.executeTransaction(transaction);

    }
    // run asynchronous transactions easily, with expressive builders
    public static void executeAsyncTransaction(ITransaction transaction,Transaction.Success successCallback,Transaction.Error errorCallback){

        DatabaseDefinition database = FlowManager.getDatabase(GodspeedDatabase.databaseClass);
        database.beginTransactionAsync(transaction).success(successCallback).error(errorCallback).build().execute();

    };

}
