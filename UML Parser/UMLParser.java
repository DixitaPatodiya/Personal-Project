import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.TypeParameter;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.ModifierSet;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

public class UMLParser {
	
	 public static String plantIn = "";
	 public static String cn = null;
	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
		String path  = ("D:\\Study\\USAdoc\\SJSU\\Subjects\\202\\workspace\\umlparser1\\uml-parser-test-4");
    	//String className="";
        File file = new File(path);
        // parse the file
       
        File[] files = file.listFiles();
		for(File f: files){
			if(f.getName().contains(".java")){
				
				CompilationUnit cu = JavaParser.parse(f);
				//System.out.println(f.getName());
				
				new getClassDetails().visit(cu, null);
				new MethodVisitor().visit(cu, null);
				new FieldVisitor().visit(cu, null);
				new ConstructorVisitor().visit(cu, null);
			System.out.println(plantIn);
			FileOutputStream png = new FileOutputStream(new File("D:\\Study\\USAdoc\\SJSU\\Subjects\\202\\workspace\\umlparser1\\test1.png"));
			String source = "@startuml\n";
			//source += "skinparam classAttributeIconSize 0\n";
			source += plantIn + "\n";
			source += "@enduml\n";

			SourceStringReader reader = new SourceStringReader(source);
			reader.generateImage(png, new FileFormatOption(FileFormat.PNG, false));
			//DiagramDescription desc = reader.generateImage(png);

			png.close();
				//new ClassVisitor().visit(cu, null);
				
				}

			}
	}
	private static class MethodVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration md, Void arg) {
        	String m = null;
        	String pm = "";
            
        	if(md.getModifiers() == ModifierSet.PRIVATE){
    			m = "-";
    		}
    		else if(md.getModifiers() == ModifierSet.PUBLIC)
    		{
    			m= "+";
    		}
    		else if(md.getModifiers() == ModifierSet.PROTECTED)
    		{
    			m = "#";
    		}
        	pm += m;
        	pm += md.getName().toString();
        	pm += "() : ";
        	pm += md.getType().toString() + "\n";
        	plantIn += "\n" + cn + pm;
        			//umlString += "\n" + cn + " : " + pm;
        	//System.out.println(pm);
           // System.out.println(md.getName());
            super.visit(md, arg);
        }
    }
	private static class getClassDetails extends VoidVisitorAdapter{                       
		public void visit(ClassOrInterfaceDeclaration n, Object arg){
			cn = n.getName();
			if(n.isInterface()){
					String i = n.getName().toString();
					plantIn += "\n"+"Class "+ cn;
					//plantIn += "\n"+ "interface"+ n.getName()+"<|.. "+ cn;
					//umlString+="\n"+"interface "+interfaceName+" <|.. "+ClassName;
					//System.out.println(plantIn);
			}
				if(n.getExtends() != null){
				//System.out.println(n.getName().toString()+"Extendspara");
				plantIn += "\n"+n.getExtends().toString().replaceAll("\\[|\\]", "")+" <|-- "+ cn;
				}
				
			
			if(n.getImplements() != null){
				String g= n.getName().toString();
				
				//System.out.println(g+"Impspara");
			}super.visit(n, arg);
			//"\n"+extendName+" <|-- "+ClassName
		}
		
	}
	private static class FieldVisitor extends VoidVisitorAdapter<Void>{

		public void visit(FieldDeclaration fd, Void arg) {
			// TODO Auto-generated method stub
			String m = null;
			
			if(fd.getModifiers() == ModifierSet.PUBLIC){
				m = "+";
			}
			else if(fd.getModifiers() == ModifierSet.PRIVATE)
			{
				m = "-";
			}
			else if(fd.getModifiers() == ModifierSet.PROTECTED)
			{
				m = "#";
			}
			String v = "";
			v += m;
			v += fd.getVariables().toString().replaceAll("\\[|\\]", "");
			v += " : ";
			v += fd.getType();
		//	System.out.println(v);
			//super.visit(n, arg);
		}
		
	}

private static class ConstructorVisitor extends VoidVisitorAdapter<Void> {
		public void visit(ConstructorDeclaration n, Void arg) {
	        

			String modifier = null;
			
			if(n.getModifiers() == ModifierSet.PUBLIC){
				modifier = "+";
			}
			else if(n.getModifiers() == ModifierSet.PRIVATE)
			{
				modifier = "-";
			}
			else if(n.getModifiers() == ModifierSet.PROTECTED)
			{
				modifier = "#";
			}
		//	String p = n.getTypeParameters();
			
			
	        System.out.println(modifier + n.getName().toString() + "() : ");
	        super.visit(n, arg);
	    }
	}
	
}






