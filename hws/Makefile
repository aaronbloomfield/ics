.SUFFIXES: .md .html

markdown:
	@find . | grep -e "\.md$$" | grep -v reveal.js | grep -v node_modules | sed s/.md$$/.html/g | awk '{print "make -s "$$1}' | bash

.md.html:
	pathprefix=`echo $< | tr -d -c '/' | sed -r 's/\//..\//g'` && \
		pandoc --standalone -V "pagetitle:$$(head -1 $<)" -H tabs.js -f markdown -c $$pathprefix"../markdown.css" --columns=9999 -t html5 -o $@ $<
	@./maketabs.py $@
	@echo wrote $@
