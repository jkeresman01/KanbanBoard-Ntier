package com.keresman.kanbanboard.s3;

/** Service interface for interacting with an S3-compatible object store. */
public interface S3Service {

  /**
   * Uploads an object to the S3 bucket.
   *
   * @param key the object key (path) in the bucket
   * @param data the byte content of the object
   */
  void putObject(String key, byte[] data);

  /**
   * Retrieves an object from the S3 bucket.
   *
   * @param key the object key (path) in the bucket
   * @return the byte content of the object
   */
  byte[] getObject(String key);

  /**
   * Deletes an object from the S3 bucket.
   *
   * @param key the object key (path) in the bucket
   */
  void deleteObject(String key);
}
