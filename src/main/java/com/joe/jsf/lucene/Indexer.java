package com.joe.jsf.lucene;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {

  private IndexWriter writer;

  public Indexer(String indexDirectoryPath) throws IOException {
    // this directory will contain the indexes
    Directory indexDirectory = FSDirectory.open(new File(indexDirectoryPath).toPath());

    // create the indexer
    writer = new IndexWriter(indexDirectory, new IndexWriterConfig(new StandardAnalyzer()));
    writer.deleteAll();
  }

  public void close() throws CorruptIndexException, IOException {
    writer.close();
  }

  private Document getDocument(File file) throws IOException {
    Document document = new Document();

    // index file contents
    Field contentField = new TextField(LuceneConstants.CONTENTS, new FileReader(file));
    // index file name
    Field fileNameField =
        new StringField(LuceneConstants.FILE_NAME, file.getName(), Field.Store.YES);
    // index file path
    Field filePathField =
        new StringField(LuceneConstants.FILE_PATH, file.getCanonicalPath(), Field.Store.YES);

    document.add(contentField);
    document.add(fileNameField);
    document.add(filePathField);

    return document;
  }

  private void indexFile(File file) throws IOException {
    System.out.println("Indexing " + file.getCanonicalPath());
    Document document = getDocument(file);
    writer.addDocument(document);
  }

  public int createIndex(String dataDirPath, FileFilter filter) throws IOException {
    // get all files in the data directory
    File[] files = new File(dataDirPath).listFiles();

    for (File file : files) {
      if (!file.isDirectory()
          && !file.isHidden()
          && file.exists()
          && file.canRead()
          && filter.accept(file)) {
        indexFile(file);
      }
    }
    return writer.numDocs();
  }
}
