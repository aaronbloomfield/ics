#!/usr/bin/python3

# based on the example at:
# https://www.w3schools.com/howto/howto_js_tabs.asp

from html.parser import HTMLParser
import os, sys

assert len(sys.argv) == 2
assert os.path.exists(sys.argv[1])

if sys.argv[1] not in ['hw-rsa.html','hw-hashing.html', 'hw-fuzzing.html', 'hw-networks.html',
						'hw-sql-xss-csrf.html', 'hw-cryptocurrency.html','hw-rootkits.html',
						'hw-buffer.html', 'hw-forensics.html', 'hw-paranoia.html', 'hw-ethics.html',
						'hw-tricky-jump.html']:
	exit()

outfilename = sys.argv[1].replace(".html","") + "-tabbed.html"

#print("index.md:\t",os.path.getctime('index.md'))
#print("index.html:\t",os.path.getctime('index.html'))
#print("index-full.html:\t",os.path.getctime('index-full.html'))
#print(os.path.getctime('index.html') == os.path.getctime('index-full.html'))

fin = open(sys.argv[1],"r")
data = fin.read()

header = "<!DOCTYPE html>\n"
body = ""
tabs = []

class MyHTMLParser(HTMLParser):
	firsttime = True
	in_code = False
	
	def handle_starttag(self, tag, attrs):
		global body, header, tabs
		if tag == 'h3':
			assert len(attrs) == 1
			assert attrs[0][0] == 'id'
			assert len(attrs[0]) == 2
			tabs.append(attrs[0][1])
			if self.firsttime:
				# back up 4 characters (remove the '</p>'), add a navigation link
				header += body.strip()[:-4] + " | <a href='" + sys.argv[1] + "'>view one-page version</a></p>"
				body = ""
			else:
				body += "</div>"
			self.firsttime = False
			body += "<div id='t" + attrs[0][1] + "' class='tabcontent'>"
		if tag == 'code':
			self.in_code = True
		body += "<"+tag
		for attr in attrs:
			body +=  " "+attr[0]+"="+"'"+attr[1]+"'"
		body += ">"

	def handle_endtag(self, tag):
		global body
		#print("Encountered an end tag :", tag)
		if tag == "body":
			body += "</div><script>document.getElementById('defaultOpen').click();</script>"
		if tag == 'code':
			in_code = False
		body += "</"+tag+">"

	def handle_data(self, data):
		global body
		if data != '':
			#print("Encountered some data  :", data.strip())
			if self.in_code:
				body += data.replace("<","&lt;").replace(">","&gt;")
			else:
				body += data


replacements = [ ("Ec","EC"),("Io","I/O"),("Tbtc","tBTC"),("P2Pkh","P2PKH"),("Tokendex","TokenDEX"),
				 ("Web3.Py","Web3.py"),("Nft","NFT"),("Nfts","NFTs"),("Dao","DAO"),("Eth","ETH"),
				 ("Fake","(fake)"),("Erc","ERC"),("Html","HTML"),("Iauctioneer","IAuctioneer"),
				 ("Igradebook","IGradebook"),("Tc","TC"),("Tokencc","TokenCC"),("Css","CSS"),
				 ("&","&amp;"),("Dex","DEX"),("Metamask","MetaMask"),
				 ("Ascii","ASCII"),("Crc","CRC"),("Md5","MD5"),("Sql","SQL"),("Csrf","CSRF"),("Xss","XSS"),
				 ("Aslr","ASLR"),("Chatgpt","ChatGPT"),("Elf","ELF")]

parser = MyHTMLParser()
parser.feed(data)

with open(outfilename,"w") as fout:
	print(header,file=fout,end='')
	print("<div class='tab'>",file=fout)
	firsttime = True
	for tab in tabs:
		if tab[0:5] in ["step-","part-","task-"]:
			name = " " + tab[7:].replace("-"," ").title() + " "
		else:
			name = " " + tab.replace("-"," ").title() + " "
		for x,y in replacements:
			name = name.replace(" "+x+" "," "+y+" ")
		name = name.strip()
		id = ""
		if firsttime:
			id=" id='defaultOpen'"
			firsttime = False
		print("<button class='tablinks' onclick=\"openTab(event,'t" + tab + "')\"" + id + ">" + name + "</button>",file=fout)
	print("</div>",file=fout)
	print(body,file=fout)

# add in the 'tabbed version' link back into the source
os.system("sed s_'<p><a href=\"index.html\">Go up to the ICS HW page</a> (<a href=\"index.md\">md</a>)</p>'_'<p><a href=\"index.html\">Go up to the ICS HW page</a> (<a href=\"index.md\">md</a>) | <a href=\"" + outfilename + \
		"\">view tabbed version</a></p>'_g " + sys.argv[1] + " > tmp.foo; /bin/mv -f tmp.foo " + sys.argv[1])
