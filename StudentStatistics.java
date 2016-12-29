// Edwin Betancur
// Java A7
// This program allows professor plum to enter each
// students name and grade/score. He then has a
// choice to choose whether he wants to calculate
// an average with or without the lowest grade/score.
// He also has the option to choose whether he wants
// the maximum, minimum, letter grades ouput
// and/or a roster list output along with the average
// or lowest average grade/score.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

/**
   The StudentStatistics class sets the students
   ojects, panels, buttons, textfields, radio buttons
   and checkboxs.
*/

public class StudentStatistics extends JFrame
{

	private DecimalFormat decimalFormat =
	new DecimalFormat("#00.#"); 					// Create a decimal format object

	private final int SIZE = 5;					// Number of students.
	private int counter = 0;						// Holds the count of entered students

   private Student[] students =
   new Student[SIZE]; 								// Array of student objects

	// for adding panels to frame
   private JPanel statisticsPanel; 				// The statistics panel
   private JPanel studentInfoPanel;  			// The student infomation panel
   private JPanel buttonPanel;    				// To hold the buttons

   // for the buttonPanel
   private JButton calcButton;    				/* To calculate the average,
   															low average and display
   															Maximum scores, Minimum scores,
   															letter grade and roster list. */

   private JButton exitButton;    				// To exit the application

   // for the studentInfoPanel
   private JTextField studentNameField;		// For input of the students' name
   private JTextField studentScoreField;		// For input of the students' score
   private JButton addStudentButton;			// To add the student

	// for the statisticsPanel
	private JRadioButton average;  				// To select Average
	private JRadioButton averageDropLow;  		// To select Average Drops Lowest Test
   private ButtonGroup averageBg;      		// Radio button group

	// for the statisticsPanel
   private JCheckBox maximum;  					// To disaply maximum score
   private JCheckBox minimum;       			// To display minimum score
   private JCheckBox letterGrades;   			// To display names with letter grades/scores
   private JCheckBox listRoster; 				// To display a list with names and number grades/scores


   /**
      This constructor builds the GUI by setting the title,
      frame, layout and panels.
   */

   public StudentStatistics()
   {
      // Display a title.
      setTitle("Edwin Betancur");

      // Specify an action for the close button.
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // Create a BorderLayout manager.
      setLayout(new BorderLayout());

      // Create the custom panels.
      buildStudentInfoPanel();
      buildStatisticsPanel();

      // Create the button panel.
      buildButtonPanel();

      // Add the components to the content pane.
      add(studentInfoPanel, BorderLayout.NORTH);
      add(statisticsPanel, BorderLayout.CENTER);
      add(buttonPanel, BorderLayout.SOUTH);

      // Pack the contents of the window and display it.
      pack();
      setVisible(true);

   }

   /**
      The buildButtonPanel method builds the button panel.
   */

   private void buildButtonPanel()
   {
      // Create a panel for the buttons.
      buttonPanel = new JPanel();

      // Create the buttons.
      calcButton = new JButton("Calculate Statistics");
      exitButton = new JButton("Exit");

      // Register the action listeners.
      calcButton.addActionListener(new CalcButtonListener());
      exitButton.addActionListener(new ExitButtonListener());

      // Add the buttons to the button panel.
      buttonPanel.add(calcButton);
      buttonPanel.add(exitButton);

		// Disable the calculate button
      calcButton.setEnabled(false);

   }

	private void buildStudentInfoPanel()
	{
		// Create a panel for entering students' information
		studentInfoPanel = new JPanel();

		// Create a flowlayout
		studentInfoPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		// Create a textfield for student name input
		studentNameField = new JTextField(20);

		// Create a student score field
		studentScoreField = new JTextField(3);

		// Create a button to add the student infomation
		addStudentButton = new JButton("Add Student");

		// Give the addStudentButton a listener
		 addStudentButton.addActionListener(
			 new AddStudentButtonListener());


      // Add a border around the panel.
		studentInfoPanel.setBorder(
		BorderFactory.createTitledBorder("Student Info"));

		// Add the componets to the panel
		studentInfoPanel.add(studentNameField);
		studentInfoPanel.add(studentScoreField);
		studentInfoPanel.add(addStudentButton);

	}

	/**
		The buildStatisticsPanel method creates
		a panel, sets a layout, creates radio
		buttons, checkboxes. Sets a layout
		and adds buttons and checkboxes
		to the panel.
	*/

	private void buildStatisticsPanel()
	{
		// Create a panel.
		statisticsPanel = new JPanel();

      // Create a GridLayout manager with
      // six rows and one column.
      statisticsPanel.setLayout(new GridLayout(6, 1));

      // Create radio buttons.
		average = new JRadioButton("Average", true);
		averageDropLow = new JRadioButton("Average Drops Lowest Test");

		 // Group the radio buttons.
		averageBg = new ButtonGroup();
		averageBg.add(average);
		averageBg.add(averageDropLow);


		// Create the check boxes.
      maximum = new JCheckBox("Maximum");
      minimum = new JCheckBox("Minimum");
      letterGrades = new JCheckBox("Letter Grades");
      listRoster = new JCheckBox("List Roster");

      // Add a border around the panel.
      statisticsPanel.setBorder(
			BorderFactory.createTitledBorder("Statistics"));

      // Add the check boxes and radio buttons to the panel.
      statisticsPanel.add(average);
      statisticsPanel.add(averageDropLow);
      statisticsPanel.add(maximum);
      statisticsPanel.add(minimum);
      statisticsPanel.add(letterGrades);
      statisticsPanel.add(listRoster);
	}

   /**
      This CalcButtonListener class handles the event when
      the user clicks the Calculate button.
   */

   private class CalcButtonListener implements ActionListener
   {
		/**
			The actionPerformed method concatenates
			strings to form the output.
			@param e The action event.
		*/

      public void actionPerformed(ActionEvent e)
      {
			// Starts off with an empty string.
			String output = "";

			// Concatenate the average output.
			output += "STATISTICS:\n\n" + "AVERAGE: " +
						 decimalFormat.format(findAverage()) + "\n";

			if ( maximum.isSelected() )
			{
				// Concatenate the maximum output.
				output += "MAXIMUM: " + findMaximum() + "\n";
			}


			if ( minimum.isSelected() )
			{
				// Concatenate the minimum output.
				output += "MINIMUM: " + findMinimum() + "\n\n";
			}


			if ( letterGrades.isSelected() )
			{
				// Concatenate the letter grade output.
				output += showLetterGrades() + "\n\n";
			}


			if (listRoster.isSelected() )
			{
				// Concatenate the list students output.
				output += listStudents() + "\n\n";
			}

			// Displays the output.
			JOptionPane.showMessageDialog(null, output);

		}
   }

   /**
      The ExitButtonListener class handles the event
      when the user clicks the exitButton.
    */

   private class ExitButtonListener implements ActionListener
   {

		/**
			The actionPerformed method closes the application.
			@param e The action event.
		*/

      public void actionPerformed(ActionEvent e)
      {
          System.exit(0);
      }
   }

	/**
		The AddStudentListener class handles the events
		when the user clicks the addStudentButton.
	*/

   private class AddStudentButtonListener implements ActionListener
   {
		/**
			The actionPerformed method gets input from
			the textfields and vaildates it.
			@param e The action event
		*/
		public void actionPerformed(ActionEvent e)
		{

			String nameInput;									// The students name.
			String scoreInput;								// The students grade/score.
			int score;											// The students grade/score.


			nameInput = studentNameField.getText(); 	// Store students name.
			scoreInput = studentScoreField.getText();	// Store students score.

			// Check for an empty string input from the user.
			if (nameInput.equals(""))
			{

				JOptionPane.showMessageDialog(null, "Please " +
				"enter a name");

				// Set focus on the name textfield.
				studentNameField.requestFocus();

			}
			else if (scoreInput.equals(""))
			{

				JOptionPane.showMessageDialog(null, "Please " +
				"enter a score");

				// Set focus on the score textfield.
				studentScoreField.requestFocus();
			}
			else
			{
				// Add number of inputs.
				counter += 1;

				// Store the students score as an integer
				score = Integer.parseInt(scoreInput);

				// Create a new student object
				students[counter - 1] = new Student(nameInput, score);
			}

		   if (counter == SIZE)
			{
				// Enable the calculate button
				calcButton.setEnabled(true);

				// Disable the add student button
				addStudentButton.setEnabled(false);

				// Disable the name field
				studentNameField.setEnabled(false);

				// Disable the score field
				studentScoreField.setEnabled(false);

			}
			else
			{
				// Disable the calculate button
				calcButton.setEnabled(false);
			}

		}
	}

	/**
		The findAverage method checks for the selected
		average radio buttons, adds the running total
		then computes the average or the dropped average.
		@return The average grade/score.
	*/

	public double findAverage()
	{
		double averageGrade = 0;					// Holds average grade.
		double total = 0; 							// An accumulator.
		int lowest = students[0].getGrade();	// The lowest grade.


		if ( average.isSelected() )
		{
			// Total all the students scores.
			for (int index = 0; index < SIZE; index++)
			{
				// Accumulate each students score.
				total += students[index].getGrade();
			}

			// Store the average grade/score.
			averageGrade = total / SIZE;
		}


		if ( averageDropLow.isSelected() )
		{
			for (int index = 0; index < SIZE; index++)
			{
				// Test each students grade/score to lowest grade.
				if (students[index].getGrade() < lowest)
				{
					// Store the lowest grade.
					lowest = students[index].getGrade();
				}
			}

			for (int index = 0; index < SIZE; index++)
			{

				total += students[index].getGrade();
			}

			// Subtract the lowest grade from the total.
			total -= lowest;

			// Store the average grade/score.
			averageGrade = total / (SIZE - 1);
		}

		return averageGrade;
	}

	/**
		The findMaximum method finds the maximum
		grade out of all students.
		@return The highest grade.
	*/

	public int findMaximum()
	{
		// Store the first students grade/score as highest.
		int highest = students[0].getGrade();

		for (int index = 1; index < SIZE; index++)
		{
			// Compare each stdents grade/score to the
			// highest grade/score.
			if (students[index].getGrade() > highest)
			{
				// Store the highest grade/score.
				highest = students[index].getGrade();
			}
		}

		return highest;
	}

	/**
		The findMinimum method finds the minimum
		grade/score out of all students.
		@returns The lowest grade.
	*/

	public int findMinimum()
	{
		// Store the first students grade/score as lowest.
		int lowest = students[0].getGrade();

		for (int index = 1; index < SIZE; index++)
		{
			// Compare each students grade/score to the
			// lowest grade/score.
			if (students[index].getGrade() < lowest)
			{
				// Store the lowest grade/score.
				lowest = students[index].getGrade();
			}
		}

		return lowest;
	}

	/**
		The showLetterGrades method checks to
		see what letter grade a student
		earns and adds output based on the
		outcome.
		@return The letter grade output.
	*/

	public String showLetterGrades()
	{
		// Start with a title for the output.
		String letterGradeOutput = "LETTER GRADES:";

		for (int index = 0; index < SIZE; index++)
		{

			letterGradeOutput += "\n";

			if (students[index].getGrade() >= 90)
			{
				// Store the students name with his/her
				// letter grade/score.
				letterGradeOutput += students[index].getName() +
				": " + "A";
			}
			else if (students[index].getGrade() >= 80)
			{

				letterGradeOutput += students[index].getName() +
				": " + "B";
			}
			else if (students[index].getGrade() >= 70)
			{

				letterGradeOutput += students[index].getName() +
				": " + "C";
			}
			else if (students[index].getGrade() >= 60)
			{

				letterGradeOutput += students[index].getName() +
				": " + "D";
			}
			else if (students[index].getGrade() >= 50)
			{

				letterGradeOutput += students[index].getName() +
				": " + "F";
			}

		}

		return letterGradeOutput;
	}

	/**
		The listStudents method generates output
		of the students name and grade/score.
		@return The list output.
	*/

	public String listStudents()
	{
		// Start with a title
		String listOutput = "ROSTER:\n";

		for (int index = 0; index < SIZE; index++)
		{
			// Store students name with his/her grade/score.
			listOutput += students[index].getName() +
					": " + students[index].getGrade() +
				   "\n";
		}

		return listOutput;
	}

   /**
	   This method creates an instance of the StudentStatistics class
	   which displays the GUI for Professor Plum's application.
	*/

	public static void main(String[] args)
	{

		StudentStatistics gui = new StudentStatistics();
	}

}