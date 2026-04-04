.SUFFIXES: .md .html

markdown:
	@cd hws && make -s
	@find . | grep -e "\.md$$" | grep -v reveal.js | grep -v node_modules | sed s/.md$$/.html/g | awk '{print "make -s "$$1}' | bash
	@/bin/cp -f readme.html index.html

.md.html:
	pathprefix=`echo $< | tr -d -c '/' | sed -r 's/\//..\//g'` && \
		pandoc --template `git rev-parse --show-toplevel`/pandoc-template.html --metadata lang="en" --standalone -V "pagetitle:$$(head -1 $<)" -f markdown -c $$pathprefix"markdown.css" --columns=9999 -t html5 -o $@ $<
	@echo wrote $@

touchall:
	find . | grep "\.md$$" | awk '{print "touch "$$1}' | bash

clean:
	/bin/rm -rf *~ */*~ */*/*~ */*/*/*~

csspretty:
	@# requires installation of cssbeautify as per https://prefetch.net/blog/2017/12/02/formatting-css-from-the-linux-command-line/
	@# awk command from https://www.linuxquestions.org/questions/programming-9/remove-css-comments-with-sed-776853-print/
	cat slides/quarto_files/revealjs/dist/theme/quarto.css | tr -d '\n' | awk -vRS="*/" '{gsub(/\/\*.*/,"")}1' | node_modules/.bin/cssbeautify-cli -s -w css.out
	/bin/mv -f css.out slides/quarto_files/revealjs/dist/theme/quarto.css

all:
	make
	cd slides && make
	cd uva && make

all-source-highlight:
	cd hws/buffer/ && make source && cd ../..
	cd hws/fuzzing/ && make source && cd ../..
	cd hws/rsa/ && make source && cd ../..
	cd hws/tricky-jump/ && make source && cd ../..
	cd slides/code/buffer-overflows/ && make source && cd ../../..
	cd slides/code/exploits/ && make source && cd ../../..
