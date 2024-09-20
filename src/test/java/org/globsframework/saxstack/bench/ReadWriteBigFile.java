package org.globsframework.saxstack.bench;

import org.globsframework.saxstack.writer.SaxStackWriter;
import org.globsframework.saxstack.writer.XmlNodeBuilder;
import org.globsframework.saxstack.writer.XmlRootBuilder;
import org.globsframework.saxstack.writer.XmlTag;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReadWriteBigFile {

    public static void main(String[] args) throws IOException {
        XmlRootBuilder rootBuilder = new XmlRootBuilder() {
            public String getTagName() {
                return "root";
            }

            public XmlNodeBuilder[] process(XmlTag rootTag) throws IOException {
                return new XmlNodeBuilder[]{
                        new TestXmlNodeBuilder(6, 5)
                };
            }
        };

        File file = File.createTempFile("testBigXml", ".xml");
//    file.deleteOnExit();
        long start = System.currentTimeMillis();
        SaxStackWriter.write(new BufferedWriter(new FileWriter(file)), rootBuilder);
        System.out.println("ReadWriteBigFile.main " + file.getAbsolutePath());
        System.out.println("ReadWriteBigFile.main " + file.length() + " in " + (System.currentTimeMillis() - start));
    }

    private static class TestXmlNodeBuilder implements XmlNodeBuilder {
        int level;
        int count;
        int tmpCount;
        String name;
        XmlNodeBuilder childs[];

        public TestXmlNodeBuilder(int count, int level) {
            this.count = count;
            this.tmpCount = count;
            this.level = level;
            name = "name_" + level;
            if (level > 0) {
                childs = new XmlNodeBuilder[]{
                        new TestXmlNodeBuilder(count, level - 1)
                };
            } else {
                childs = new XmlNodeBuilder[0];
            }
        }

        public boolean hasNext() {
            return tmpCount >= 0;
        }

        public String getNextTagName() {
            return name + "_" + tmpCount;
        }

        public XmlNodeBuilder[] processNext(XmlTag tag) throws IOException {
            tmpCount--;
            tag.addAttribute("AnAttribute", "some attr value");
            if (childs.length == 0) {
                tag.addValue("Some value");
            }
            return childs;
        }

        public void levelDone() {
            tmpCount = count;
        }
    }
}
