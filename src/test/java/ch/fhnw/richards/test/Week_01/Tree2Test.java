package ch.fhnw.richards.test.Week_01;

import ch.fhnw.richards.Week_01.Tree2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Tree2Test {
        private Tree2<String> family = new Tree2<>();

        @BeforeEach
        public void createFamily() {
            family.setRoot("anna");
            family.addChild("anna", "clophas");
            family.addChild("anna", "genevieve");
            family.addChild("anna", "della");
            family.addChild("anna", "james");
            family.addChild("anna", "betty");
            family.addChild("anna", "bonny");
            family.addChild("clophas", "sue");
            family.addChild("clophas", "chris");
            family.addChild("genevieve", "leesa");
            family.addChild("genevieve", "bobby");
            family.addChild("genevieve", "terry");
            family.addChild("della", "brad");
            family.addChild("james", "jim");
            family.addChild("james", "jon");
            family.addChild("james", "jene");
            family.addChild("betty", "tom");
            family.addChild("betty", "tammy");
            family.addChild("bonny", "todd");
            family.addChild("bonny", "wes");
            family.addChild("bonny", "kristie");
        }

        /**
         * A quick'n'dirty test of the Tree2 class.
         *
         * A more disciplined approach would include a test method that targets each aspect.
         * For example: One method to test adding children: are they added in the right place?
         * Are they rejected if duplicates?
         *
         * This test method is more "all in one", but it does test many aspects of the class.
         */
        @Test
        public void TreeTest1() {
            assertEquals(21, family.size());
            List<String> gkids = family.getChildren("genevieve");
            assertEquals(3, gkids.size());
            assertEquals("leesa", gkids.get(0));
            assertTrue(family.contains("bobby"));
            assertTrue(gkids.contains("bobby"));
            assertTrue(family.removeNode("genevieve"));
            assertFalse(family.contains("bobby"));
            assertEquals("anna", family.getRoot());
            assertFalse(family.isEmpty());
            assertFalse(family.setRoot("fred"));
            assertEquals(17, family.size());
            assertTrue(family.addChild("brad", "tynan"));
            assertEquals(18, family.size());
        }
}