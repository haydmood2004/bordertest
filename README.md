# Hayden's Character Generator (Final Project)
### NOTE: This works better in full screen :) It also may take a few seconds to load

## Introduction
This project is meant to be a substitute for myself in regards to other character creators/generators on the internet. Usually what 
I want out of it is something too specific or niche that makes it impossible to make what I want. Really, I wanted to create 
something I could add onto as I go in the future. While this is far from finished or perfect, it is a good baseline for character
build and facial build. I also wanted to try and push the limits of Java Swing's GUI, as I felt the graphics were too plain for my liking.

## How it works
This program works mostly with sliders. Three sliders control the main color of the skin(hue, saturation, and brightness), while other
sliders control the "pieces" of the character(face, eyes, mouth, nose, ears, body). The options are limited due to time, but each piece
was made with a line layer and a color layer, with the HSB sliders only affecting the color layer beneath the line. The sliders with the
pieces are controlled by switches.

Some information about the GUI, the border was made using 9 tiles, 4 for the corners, and 4 for the borders. The corners were locked in 
aspect ratio and location to the corners of the undecorated JFrame, while the borders were meant to stretch with the frame. So, top and bottom
borders could stretch width-wise, but not height-wise, and vice versa. The window can also be moved. The toolbar was created manually as well, using buttons with action 
listeners to close, minimize, and restore. The menu bar to the left with the buttons was meant to be more functional, but i completely
underestimated the scope, so it wasn't used. However, the buttons still highlight when hovered. 

The cursor was decorated and adjusted based on how it felt on my screen.

## How to use it
So, when it loads, it will load as a blank base. First, adjust the brightness of the skin so you can see the colors you are working with. 
The HSB sliders have a live update in the preview box, and the skin color will update once the thumb track is released. This is mainly so 
Java has less updates to make, making the program much faster.


For the pieces, each part is labeled with a number. I recommend clicking along instead of sliding, as it is easier to deal with.

Adjust things to your liking :)

## Future plans
Firstly, I would love to be able to zoom in if at all possible with Java. I have plans this summer to test it out on other programming 
languages now that I am at least familiar with Java, which hopefully will have a more easily customizable GUI. I want to add more parts 
as well, and adjust a few things. I am not thrilled with how the head sits on all of the body types, so maybe I will try some adjusting.
I also want to add a female option and add hair. I have hair drawn, but not exactly the time to add it (clearly :,D). Drawing the pieces
takes much longer than I thought it would, and I have way too many ideas. I want to add options for blemishes, scars, maybe tattoos, but I
am picky with those as they express personality in a specific way. I also want to include an option for clothes, too, which I am really 
sad I couldn't get to. I had some ideas on how to address it, and have some clothing blanks that could be customized by color. Maybe even 
primary and secondary colors, depending on the outfit. Same with more fantastical elements, like wings, tails, gills, something cool like 
that. One day having options for pets would be cool too.

## Challenges
There is not a lot of information out there on heavy swing customization. The information exists, clearly, but it is difficult to find and 
learn about(Ended up buying a book...). I am sure there are much easier ways to do all of this. I get very overwhelmed with things that feel 
crowded, which this does. Sometimes I couldn't tell new code from old code and things would repeat itself, causing problems. So, I would 
use copilot to consolidate code, which turned out to be a MAJOR issue. One I fell for too many times out of desperation for some form of 
organization. Often when it would consolidate, something would tweak, which is expected. Copilot is also having issues i believe, as they 
are limiting the use of pro plans according to their website, which I assume would prevent ours through our school pro benifits. This likely
didnt help. Also, going back from copilot editing the code like that is not easy at all. It got to a point where I had a separate document 
to put the code in to preserve it in case copilot was weird with consolidation.

## Conclusion
I am working on this over the summer, especially since i wont have an internship this summer. i am excited, and hopefully i can do cool 
things with the program along the way.

If there are any odd sentences, I am a little sleep deprived, but excited to share this program
