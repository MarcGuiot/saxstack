package org.globsframework.saxstack.sample;

import org.globsframework.saxstack.writer.XmlTag;
import org.globsframework.saxstack.writer.XmlWriter;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.StringWriter;

public class SampleWriterWithXmlTag {

  @Test
  public void testDoc() throws Exception {
    StringWriter writer = new StringWriter();
    XmlTag tag = XmlWriter.startTag(writer, "root");
    tag.createChildTag("Level1")
      .addAttribute("attr1", "val1")
      .addAttribute("attr2", "val2")
      .createChildTag("level2")
      .addValue("Some Data")
      .end()
      .end()
      .end();

    System.out.println("SampleWriterWithXmlTag.testName " + writer.toString());
  }
}
