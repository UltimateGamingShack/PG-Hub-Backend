package com.pghub.user.exception;
/**
   * This is the custom exception class for PG Hub User Microservice
 */
public class PgHubException extends Exception{

    public PgHubException(String message){
        super(message);
    }
}
