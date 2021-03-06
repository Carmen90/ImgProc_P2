package sip.p1;
import org.apache.commons.cli.*;
import operations.*;
import operations.geometric.*;
import operations.histogram.*;
import filters.*;

public class Main {

	public static void main(String[] args) {
		try {
			Operation op = parseCommands(args);
            if(op != null)
            	op.performOperation();
            else
            	System.out.println(Constants.HELP);
		} catch(InterruptedException | ParseException e){
			System.out.println(Constants.HELP);
			e.printStackTrace();
		}
	}
	
	private static Operation parseCommands(String[] args) throws ParseException, InterruptedException {
		Options options = generateOption();
		CommandLineParser parser = null;
		CommandLine cmdLine      = null;
		parser  = new BasicParser();
		cmdLine = parser.parse(options, args);
		String img;
		Operation op = null;
		if(cmdLine.hasOption("-b")){
			img = cmdLine.getOptionValue("b");
			op = new Brightness(img);
		}
		if(cmdLine.hasOption("-n")){
			img = cmdLine.getOptionValue("n");
			op = new Negative(img);
		}
		if(cmdLine.hasOption("-c")){
			String aux = cmdLine.getOptionValue("c");
			float factor = Float.valueOf(aux);
			op = new Contrast(args[2], factor);
		}
		if(cmdLine.hasOption("-hf")){
			img = cmdLine.getOptionValue("hf");
			op = new HorizontalFlip(img);
		}
		if(cmdLine.hasOption("-vf")){
			img = cmdLine.getOptionValue("vf");
			op = new VerticalFlip(img);
		}
		if(cmdLine.hasOption("-df")){
			img = cmdLine.getOptionValue("df");
			op = new DiagonalFlip(img);
		}
		if(cmdLine.hasOption("-s")){
			img = cmdLine.getOptionValue("s");
			op = new Shrinking(img);
		}
		if(cmdLine.hasOption("-e")){
			img = cmdLine.getOptionValue("e");
			op = new Enlarge(img);
		}
		if(cmdLine.hasOption("-a")){
            img = cmdLine.getOptionValue("a");
            op = new AlphaTrimmed(img);
        }
		if(cmdLine.hasOption("-gmf")){
            img = cmdLine.getOptionValue("gmf");
            op = new Geometric(img);
        }
        if(cmdLine.hasOption("-mse")){
            img = cmdLine.getOptionValue("mse");
            op = new MeanSquareError(img);
        }
        if(cmdLine.hasOption("-pmse")){
            img = cmdLine.getOptionValue("pmse");
            op = new PeakMeanSquareError(img);
        }
        if(cmdLine.hasOption("-snr")){
            img = cmdLine.getOptionValue("snr");
            op = new PeakMeanSquareError(img);
        }
        if(cmdLine.hasOption("-histogram")){
        	String aux = cmdLine.getOptionValue("histogram");
			char channel = aux.toCharArray()[0];
            op = new Histogram(args[2], channel);
        }
        if(cmdLine.hasOption("-hpower")){
        	int [][] h = new int [3][3];
        	/*for(int i = 0; i<3; i++){
        		h[0][i]= 1;
        		h[2][i]= -1;
        	}
        	h [1][0] = 1;
        	h [1][1] = -2;
        	h [1][2] = 1;*/
        	h[0][0] = 1; h[0][1] = 1; h[0][2] = 1;
        	h[1][0] = -1; h[1][1] = -2; h[1][2] = 1;
        	h[2][0] = -1; h[2][1] = -1; h[2][2] = 1;
        	img = cmdLine.getOptionValue("hpower");
            op = new HPower(img, h);
        }
        if(cmdLine.hasOption("-hpowerconcret")){
        	img = cmdLine.getOptionValue("hpowerconcret");
            op = new HPowerConcret(img);
        }
        /*
        if(cmdLine.hasOption("-cmean")){
        	String aux = cmdLine.getOptionValue("cmean");
        	char channel = aux.toCharArray()[0];
            op = new Mean(args[2], channel);
        }
        if(cmdLine.hasOption("-cvariance")){
            img = cmdLine.getOptionValue("cvariance");
            op = new Variance(img);
        }
        */
        return op;
	}

	private static Options generateOption() {
		Options options = new Options();
		options.addOption("b", "brightness" , true, "--brightness [-argument=value [...]]");
		options.addOption("n", "negative", true, "--negative [-argument=value [...]]");
		options.addOption("c", "contrast"  , true, "--contrast [-argument=value [...]]");
		options.addOption("hf", "hflip"  , true, "--hflip [-argument=value [...]]");
		options.addOption("vf", "vflip"  , true, "--vflip [-argument=value [...]]");
		options.addOption("df", "dflip"  , true, "--dflip [-argument=value [...]]");
		options.addOption("s", "shrinking"  , true, "--shrinking [-argument=value [...]]");
		options.addOption("e", "enlarge"  , true, "--enlarge [-argument=value [...]]");
		options.addOption("gmf", "geometric-mean filter"  , true, "--gmean [-argument=value [...]]");
		options.addOption("a", "alpha-trimmed"  , true, "--alpha-trimmed [-argument=value [...]]");
		options.addOption("mse", "mean-square error"  , true, "--mean-square-error [-argument=value [...]]");
		options.addOption("pmse", "peak-mean-square error"  , true, "--peak-mean-square error [-argument=value [...]]");
		options.addOption("snr", "signal-noise ratio"  , true, "--signal-noise-ratio [-argument=value [...]]");
		options.addOption("histogram", "histogram"  , true, "--histogram [-argument=value [R|G|B]]");
		options.addOption("hpower", "hpower"  , true, "--hpower [-argument=value [R|G|B]]");
		options.addOption("hpowerconcret", "hpowerconcret"  , true, "--hpowerconcret [-argument=value [R|G|B]]");
		//options.addOption("cmean", "cmean"  , true, "--cmean [-argument=value [R|G|B]]");
		return options;
	}

}
