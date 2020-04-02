import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.junit.Before;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class LuceneFormat {
    private IndexWriter indexWriter;

    @Before
    public void init() throws Exception{
        indexWriter = new IndexWriter(FSDirectory.open(new File("D:\\BaiduNetdiskDownload\\java\\12-lucene\\indexLib").toPath()),new IndexWriterConfig(new IKAnalyzer()));
    }

    @Test
    public void deleteAllIndex() throws IOException {
        indexWriter.deleteAll();
        indexWriter.close();
    }


    @Test
    public void deleteByQuery() throws Exception{
        //删除文件名包含apache的文档
        long l = indexWriter.deleteDocuments(new Term("fileName", "apache"));
        System.out.println(l);
        indexWriter.close();
    }

    @Test
    public void addDocument() throws Exception{
        File file = new File("D:\\BaiduNetdiskDownload\\java\\12-lucene\\笔记.txt");
        Document document = new Document();
        document.add(new TextField("fileName",file.getName(), Field.Store.YES));
        document.add(new StringField("filePath",file.getPath(), Field.Store.YES));
        document.add(new LongPoint("fileSize", FileUtils.sizeOf(file)));
        document.add(new TextField("fileName",file.getName(), Field.Store.YES));
        indexWriter.addDocument(document);
        indexWriter.close();
    }
}
