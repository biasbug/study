import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;

public class LuceneDemo {

    //创建索引库
    @Test
    public void createIndex() throws Exception{
        //1、创建一个Director对象，指定索引库保存的位置。
        Directory directory = FSDirectory.open(new File("D:\\BaiduNetdiskDownload\\java\\12-lucene\\indexLib").toPath());
        //索引页可以储存在内存中，但是很少使用该方式
        //Directory directory = new RAMDirectory();

        //2、基于Directory对象创建一个IndexWriter对象
        IndexWriterConfig config = new IndexWriterConfig();
        IndexWriter indexWriter = new IndexWriter(directory,config);

        //3、读取磁盘上的文件,获取每个文件的内容
        File[] files = new File("D:\\BaiduNetdiskDownload\\java\\12-lucene\\02.参考资料\\searchsource").listFiles();
        for (File file : files) {
            //获取文档路径
            String filePath = file.getPath();
            //获取文档名称
            String fileName = file.getName();
            //获取文档大小
            long fileSize = FileUtils.sizeOf(file);
            //获取文档内容
            String fileContext = FileUtils.readFileToString(file, "utf-8");

            //4、把获取的文件内容解析到域中
            Field filePathField = new TextField("filePath",filePath,Field.Store.YES);
            Field fileNameField = new TextField("fileName",fileName,Field.Store.YES);
            Field fileSizeField = new TextField("fileSize",fileSize+"",Field.Store.YES);
            Field fileContextField = new TextField("fileContext",fileContext,Field.Store.YES);

            //5、创建文档对象，将域添加到文档对象中
            // 此时文档对象就是由多个域构成，多个文档对象可能包含相同的域
            Document document = new Document();
            document.add(filePathField);
            document.add(fileNameField);
            document.add(fileSizeField);
            document.add(fileContextField);

            //6、把文档对象写入索引库
            indexWriter.addDocument(document);
        }
        //7、关闭indexwriter对象
        indexWriter.close();

    }

    //查询索引库
    @Test
    public void searchIndex() throws Exception{
        //1、创建一个Director对象，指定索引库的位置
        Directory directory = FSDirectory.open(new File("D:\\BaiduNetdiskDownload\\java\\12-lucene\\indexLib").toPath());
        //2、创建一个IndexReader对象
        IndexReader indexReader = DirectoryReader.open(directory);
        //3、创建一个IndexSearcher对象，构造方法中的参数indexReader对象。
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //4、创建一个Query对象，TermQuery
        Query query = new TermQuery(new Term("fileContext","spring"));
        //5、执行查询，得到一个TopDocs对象
        TopDocs topDocs = indexSearcher.search(query, 3);
        //6、取查询结果的总记录数
        long totalHits = topDocs.totalHits;
        System.out.println("查询总条数："+totalHits);
        //7、取文档列表，TopDocs对象的scoreDocs方法返回查询后的所有文档的scoreDoc对象
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            //scoreDoc.doc存储了document对象的id,通过id获取到document对象
            //8、打印文档中的内容
            Document document = indexSearcher.doc(scoreDoc.doc);
            System.out.println("文件名称:"+document.get("fileName"));
            System.out.println("文件路径:"+document.get("filePath"));
            System.out.println("文件大小:"+document.get("fileSize"));
            //System.out.println("文件内容:"+document.get("fileContext"));
            System.out.println("-----------------------------");
        }
        //9、关闭IndexReader对象
        indexReader.close();
    }


    //标准分析器分析效果
    @Test
    public void testStandardAnalyzer() throws Exception{
        //1、创建一个解析器
        //Analyzer analyzer = new StandardAnalyzer();
        Analyzer analyzer = new IKAnalyzer();
        //2、解析一个文本，第一个参数是域的名称，第二参数是一个字符串或者Reader流
        TokenStream tokenStream = analyzer.tokenStream("context", "This could be used, for example, with a RangeQuery on a formatted.龟厌不告龟兹");
//        TokenStream tokenStream = analyzer.tokenStream("context", new FileReader("D:\\BaiduNetdiskDownload\\java\\12-lucene\\02.参考资料\\searchsource\\1.create web page.txt"));
        //3、添加一个引用，可以获得每个关键词
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        //添加一个偏移量的引用，记录了关键词的开始位置以及结束位置,
        OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
        //将指针调整到头部
        tokenStream.reset();

        while (tokenStream.incrementToken()){
            //关键词的起始位置
            //System.out.println("start->" + offsetAttribute.startOffset());
            //取关键词
            System.out.println(charTermAttribute);
            //结束位置
            //System.out.println("end->" + offsetAttribute.endOffset());

        }
        tokenStream.close();
    }


}
