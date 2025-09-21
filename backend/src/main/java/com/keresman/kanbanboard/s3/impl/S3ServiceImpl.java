package com.keresman.kanbanboard.s3.impl;

import static com.keresman.kanbanboard.utility.ExceptionUtils.callUnchecked;
import static com.keresman.kanbanboard.utility.ExceptionUtils.executeUnchecked;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.keresman.kanbanboard.s3.S3Service;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** Implementation of {@link S3Service} that interacts with AWS S3. */
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

  private final AmazonS3 amazonS3;

  @Value("${aws.s3.bucket}")
  private String bucketName;

  /**
   * {@inheritDoc}
   *
   * @see S3Service#putObject(String, byte[])
   */
  public void putObject(String key, byte[] data) {
    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentLength(data.length);
    amazonS3.putObject(bucketName, key, new ByteArrayInputStream(data), objectMetadata);
  }

  /**
   * {@inheritDoc}
   *
   * @see S3Service#getObject(String)
   */
  public byte[] getObject(String key) {
    return callUnchecked(
        () -> fetchS3ObjectWithKey(key), "Failed to fetch S3 object with key: " + key);
  }

  private byte[] fetchS3ObjectWithKey(String key) throws IOException {
    try (S3ObjectInputStream s3is = amazonS3.getObject(bucketName, key).getObjectContent()) {
      return s3is.readAllBytes();
    }
  }

  /**
   * {@inheritDoc}
   *
   * @see S3Service#deleteObject(String)
   */
  public void deleteObject(String key) {
    executeUnchecked(
        () -> amazonS3.deleteObject(bucketName, key),
        "Failed to delete S3 object with key: " + key);
  }
}
