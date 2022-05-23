markdown:
	@echo Converting markdown files to html format...
	@chmod 755 utils/convert-markdown-to-html
	@utils/convert-markdown-to-html
	@git checkout uva/old/*.html
	@echo done!

oldcollab:
	@pandoc --standalone --variable "pagetitle:CS 3710 Collab Workspace" \
           --from markdown --to html5 --include-in-header=uva/collab.css --output uva/collab.html uva/collab.md

clean:
	/bin/rm -rf *~ */*~ */*/*~ */*/*/*~

