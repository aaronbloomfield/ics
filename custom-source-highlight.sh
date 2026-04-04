#!/bin/bash

# this will call source-highlight on the files provided as command line
# parameters.  It then updates the resulting .html files for the
# accessibility requirements.

for file in "$@"
do
	SED="sed -i"
	if [ x`uname` == x"Darwin" ]; then
		SED="sed -i .todel"
	fi
	source-highlight -d $file

	# ensure that the <html> tag has a lang='en' attribute
	$SED s/"<html>"/"<html lang='en'>"/ $file.html

	# ensure that the page content, which is already enclosed in a <pre> tag,
	# is enclosed in a <main> tag as well
	$SED s/"<pre>"/"<main><pre>"/ $file.html
	$SED s_"</pre>"_"</pre></main>"_ $file.html

	# the Mac (Darwin) version of sed makes backup files, so delete them
	/bin/rm -f *.todel
done
