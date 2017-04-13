
import java.io.*;
import java.util.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.*;


import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.ModifierSet;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.body.VariableDeclaratorId;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.VoidType;

public class Java2UMLParser {

ParseClassPlant parseClassPlant1 = new ParseClassPlant();
			String expressionFormat = null;
			String dir = "F:/SampleTestCases/";
			String dire = "C:/Users/Dixita/workspace/UMLParser/javaparser/src/";
			File directory = new File(args[0]);
			File[] files = directory.listFiles();	
	
	for (File fileLoop : files) {
				System.out.println(fileLoop.getName());
				if (fileLoop.isFile() && fileLoop.getName().endsWith(".java")) {

					cu = parseSourceCode.parseCode(args[0] + fileLoop.getName());
					// cu =
					// parseSourceCode.parseCode("F:/SampleTestCases/Component.java");
					ParseClass parseClass = new ParseClass();
					parseClass.visit(cu, null);
					System.out.println(expressionFormat += parseClass.getExpressionformat());
				}
			}
	

}
