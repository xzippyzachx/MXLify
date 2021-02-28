import verovio
import sys

tk = verovio.toolkit()

tk.loadFile(sys.argv[1])

svg_string = tk.renderToSVG()
# tk.setOption( "pageHeight", "10000" )
# tk.setOption( "pageWidth", "10000" )

tk.setScale(50)

tk.renderToSVGFile("Score.svg",1)