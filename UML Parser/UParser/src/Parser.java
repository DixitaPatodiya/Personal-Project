import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Method;
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

public class Parser
{
	
	 public static String plantIn = "";
	 public static String cn = null;
	 public static String inputFile;
	 public static String outputFile;
	 public static String ofile = null;
	 public static void main(String[] args)throws Exception
	 {
		// TODO Auto-generated method stub
		//inputFile =	args[0];
		//outputFile = args[1];
		//String path  = ("D:\\Study\\USAdoc\\SJSU\\Subjects\\202\\workspace\\umlparser1\\uml-parser-test-4");
    	String className="";
    	inputFile =args[0];
    	outputFile =args[1];
       File file = new File(inputFile);
       File[] files = file.listFiles();
       for(File f: files)
       {
			if(f.getName().contains(".java")){
				
				FileInputStream in = new FileInputStream(f);
				CompilationUnit cu = JavaParser.parse(in);
				//System.out.println(f.getName());
				
				new getClassDetails().visit(cu, null);
				new MethodVisitor().visit(cu, null);
				new ConstructorVisitor().visit(cu, null);
				new FieldVisitor().visit(cu, null);
				plantIn += "}" + "\n\n";}
				//new MethodVisitor().visit(cu, null);
			//FileOutputStream png = new FileOutputStream(new File("D:\\Study\\USAdoc\\SJSU\\Subjects\\202\\workspace\\umlparser1\\test1.png"));
			File fileout = new File(outputFile);
			FileOutputStream png = new FileOutputStream(fileout);
			String source = "@startuml\n";
			
			source += plantIn + "\n";
			source += "@enduml\n";
System.out.println(plantIn);
			SourceStringReader reader = new SourceStringReader(source);
			reader.generateImage(png, new FileFormatOption(FileFormat.PNG, false));
			

			png.close();
				
				//Umlgenerator Image = new Umlgenerator();
				//Image.UI(ofile);
				
       // File[] jfile = new File[0];
        // parse the file
       /* try
        { 
    		FileFilter ff = new FileFilter()
    		{
    			public boolean accept(File sourcename)
    			{
    				if(sourcename.getName().endsWith(".java"))
						{return true;}
						return false;
				}
    		};
    
        jfile = file.listFiles(ff);
        if(jfile.length == 0)
        	throw new Exception("Not Found");
        }
        catch (FileNotFoundException e) 
        {
		System.out.println("Location not Found");
		e.printStackTrace();
        } 	catch (Exception e) 
        {
		System.out.println("Not Found");
		e.printStackTrace();
        }
        //return jfile;*/



	
	
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
    		
        	pm += m;
        	
        	if(!(md.getName().substring(0,3).equals("get") || md.getName().substring(0,3).equals("set"))){
        		pm += md.getName().toString();
        		pm += "() : ";
            	pm += md.getType().toString();
            	plantIn +=  pm+"\n" ;
        	}
        	
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
					plantIn += "\n" +"Class "+ cn  +"<<interface>>" + "{" + "\n";
					//plantIn += "\n"+ "interface"+ n.getName()+"<|.. "+ cn;
					//umlString+="\n"+"interface "+interfaceName+" <|.. "+ClassName;
					//System.out.println(plantIn);
			}
				
		else if(n.getExtends() != null){
				//System.out.println(n.getName().toString()+"Extendspara");
				plantIn += "\n"+n.getExtends().toString().replaceAll("\\[|\\]", "")+" <|-- "+ cn+ "\n" +"class " +cn + "{" + "\n";
				}
				
			
		else if(n.getImplements() != null){
				String g= n.getName().toString();
				plantIn += "\n" + n.getImplements().toString().replaceAll("\\[|\\]", "") + " <|.. "   +cn+  "\n" +"class " +cn + "{" + "\n";
				
				//System.out.println(g+"Impspara");
			}
		else {
			plantIn += "\n" + "class " + cn +"{" + "\n";
			super.visit(n, arg);
			//"\n"+extendName+" <|-- "+ClassName
		}
		
	}}
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
			
			String v = "";
			v += m;
			v += fd.getVariables().toString().replaceAll("\\[|\\]", "");
			v += " : ";
			v += fd.getType();
			plantIn +=  v + "\n";
			//plantIn += "\n" + v + cn ;
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
		
		//	String p = n.getTypeParameters();
			
			plantIn += "\n"+ modifier + n.getName().toString() + "()" + "\n" ;
	        //System.out.println(modifier + n.getName().toString() + "() : ");
	        super.visit(n, arg);
	    }
	

}
/*private static class Umlgenerator {
	public void UI(String OutFile) throws IOException{
		String ofname = OutFile + ".png";
		
		String outstring = "@startuml\n";
		
		outstring += plantIn + "\n";
		outstring += "@enduml\n";
		System.out.println(plantIn);

		FileOutputStream png = new FileOutputStream(OutFile);
		SourceStringReader reader = new SourceStringReader(outstring);
		reader.generateImage(png, new FileFormatOption(FileFormat.PNG, false));
		

		png.close();
	}
	
}*/
}



