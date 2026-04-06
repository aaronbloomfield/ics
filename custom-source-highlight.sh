#!/bin/bash

# this will call source-highlight on the files provided as command line
# parameters.  It then updates the resulting .html files for the
# accessibility requirements.

for file in "$@"
do
	# sed operates differently on different systems
	SED="sed -i"
	if [ x`uname` == x"Darwin" ]; then
		SED="sed -i .todel"
	fi

	# source-highlight does not know about Solidity files, and they should be
	# formatted like C++ files
	extension=${file: -4}
	if [ x$extension == x.sol ]; then
		source-highlight -d -s cpp $file
	else
		source-highlight -d $file
	fi

	# ensure that the <html> tag has a lang='en' attribute
	$SED s/"<html>"/"<html lang='en'>"/ $file.html

	# ensure that the page content, which is already enclosed in a <pre> tag,
	# is enclosed in a <main> tag as well
	$SED s/"<pre>"/"<main><pre>"/ $file.html
	$SED s_"</pre>"_"</pre></main>"_ $file.html

	# the Mac (Darwin) version of sed makes backup files, so delete them
	/bin/rm -f *.todel
done
