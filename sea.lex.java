import java_cup.runtime.*;


class SeaScanner implements Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private boolean yy_at_bol;
	private int yy_lexical_state;

	SeaScanner (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	SeaScanner (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private SeaScanner () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int SINGLE_COMMENT = 2;
	private final int YYINITIAL = 0;
	private final int INCLUDE = 3;
	private final int MULTI_COMMENT = 1;
	private final int yy_state_dtrans[] = {
		0,
		73,
		75,
		77
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NOT_ACCEPT,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NOT_ACCEPT,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NOT_ACCEPT,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NOT_ACCEPT,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NOT_ACCEPT,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NOT_ACCEPT,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NOT_ACCEPT,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NOT_ACCEPT,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"3:9,50,4,3:2,4,3:18,50,46,10,3:3,44,3,37,38,2,41,40,42,36,1,32,15,14,13,12," +
"31,11,31,16,31,45,39,49,43,48,3:2,34:6,30:20,3:4,30,3,23,35,21,30,19,17,9,2" +
"2,7,30:2,27,26,8,30:3,24,6,25,5,30,20,33,30,18,28,3,29,47,3,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,115,
"0,1,2,1:3,3,4,1:8,5,6,1:2,7,1:3,8:3,1,8,1:5,8:10,9,10,8:8,1:5,11:2,12,13,14" +
",15,16,17,15,9,18,19,10,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36," +
"37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,8,60,6" +
"1")[0];

	private int yy_nxt[][] = unpackFromString(62,51,
"1,2,3,4,5,6,61,66,112:2,60,7:6,70,112,103,113,112:3,114,112,94,95,8,9,112,7" +
",62,112:3,10,11,12,13,14,15,16,17,18,19,20,21,65,69,5,-1:52,22,23,-1:53,112" +
",104,112:3,-1,72,112,74,112,76,24,112:11,-1:2,112:6,-1:26,7:6,-1:14,7:2,-1:" +
"3,64,-1:62,29,-1:45,30,-1:50,31,-1:12,112:5,-1,112:17,-1:2,112:6,-1:26,44:6" +
",-1:14,44:2,-1,44,-1:31,45,-1:16,45,-1:19,59:9,27,59:40,-1:5,112,105,112:3," +
"-1,112:14,106,112:2,-1:2,112:6,-1:26,7:6,-1:14,7:2,68,-1,71,64,-1:15,55,-1:" +
"60,67:6,-1:14,67:2,-1:66,32,-1:7,112:5,-1,78,112,80,112,81,25,26,112:10,-1:" +
"2,112:6,-1:64,33,-1:6,112:3,28,112,-1,82,112,83,112:14,-1:2,112:6,-1:20,112" +
":5,-1,112,34,112:15,-1:2,112:6,-1:15,1,54,63,54:48,-1:5,112:5,-1,112:3,35,1" +
"12:13,-1:2,112:6,-1:15,1,56:3,57,56:46,-1:5,112:5,-1,36,112:16,-1:2,112:6,-" +
"1:15,1,79:3,58,79:46,-1:5,112:5,-1,112,37,112:15,-1:2,112:6,-1:16,79:3,58,7" +
"9:46,-1:5,112:5,-1,112:3,38,112:13,-1:2,112:6,-1:20,112:5,-1,39,112:16,-1:2" +
",112:6,-1:20,112:5,-1,112,40,112:15,-1:2,112:6,-1:20,112:5,-1,112:3,41,112:" +
"13,-1:2,112:6,-1:20,112:5,-1,112:14,42,112:2,-1:2,112:6,-1:20,112:5,-1,112:" +
"14,43,112:2,-1:2,112:6,-1:20,112:5,-1,112:8,46,112:8,-1:2,112:6,-1:20,112:4" +
",47,-1,112:17,-1:2,112:6,-1:20,112:5,-1,112:8,48,112:8,-1:2,112:6,-1:20,112" +
":5,-1,112:8,49,112:8,-1:2,112:6,-1:20,112:5,-1,112:13,50,112:3,-1:2,112:6,-" +
"1:20,112:5,-1,112:10,51,112:6,-1:2,112:6,-1:20,112:5,-1,112:14,52,112:2,-1:" +
"2,112:6,-1:20,112:3,53,112,-1,112:17,-1:2,112:6,-1:20,84,112:4,-1,112:17,-1" +
":2,112:6,-1:20,112:5,-1,112:8,85,112:8,-1:2,112:6,-1:20,112,86,112:3,-1,112" +
":17,-1:2,112:6,-1:20,112:3,87,112,-1,112:7,88,112:9,-1:2,112:6,-1:20,112:5," +
"-1,112:7,89,112:9,-1:2,112:6,-1:20,112:5,-1,112:12,90,112:4,-1:2,112:6,-1:2" +
"0,112:2,91,112:2,-1,112:17,-1:2,112:6,-1:20,112:5,-1,112:10,92,112:6,-1:2,1" +
"12:6,-1:20,112:5,-1,112:13,93,112:3,-1:2,112:6,-1:20,112:5,-1,112:16,96,-1:" +
"2,112:6,-1:20,112:2,97,112:2,-1,112:17,-1:2,112:6,-1:20,112:2,98,112:2,-1,1" +
"12:17,-1:2,112:6,-1:20,112:5,-1,112:12,109,110,112:3,-1:2,112:6,-1:20,112:5" +
",-1,112:11,99,112:5,-1:2,112:6,-1:20,112:5,-1,112:14,111,112:2,-1:2,112:6,-" +
"1:20,112:5,-1,112:14,100,112:2,-1:2,112:6,-1:20,101,112:4,-1,112:17,-1:2,11" +
"2:6,-1:20,102,112:4,-1,112:17,-1:2,112:6,-1:20,112:5,-1,112:10,107,112:6,-1" +
":2,112:6,-1:20,112:5,-1,112:8,108,112:8,-1:2,112:6,-1:15");

	public Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {
 return null;
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{ return new Symbol(SeaSymbol.DIVIDE); }
					case -3:
						break;
					case 3:
						{ return new Symbol(SeaSymbol.STAR); }
					case -4:
						break;
					case 4:
						{ return new Symbol(SeaSymbol.error); }
					case -5:
						break;
					case 5:
						{  }
					case -6:
						break;
					case 6:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -7:
						break;
					case 7:
						{ return new Symbol(SeaSymbol.NUM, yytext()); }
					case -8:
						break;
					case 8:
						{ return new Symbol(SeaSymbol.BEGIN); }
					case -9:
						break;
					case 9:
						{ return new Symbol(SeaSymbol.END); }
					case -10:
						break;
					case 10:
						{ return new Symbol(SeaSymbol.DOT); }
					case -11:
						break;
					case 11:
						{ return new Symbol(SeaSymbol.LPAREN); }
					case -12:
						break;
					case 12:
						{ return new Symbol(SeaSymbol.RPAREN); }
					case -13:
						break;
					case 13:
						{ return new Symbol(SeaSymbol.SEMICOLON); }
					case -14:
						break;
					case 14:
						{ return new Symbol(SeaSymbol.COMMA); }
					case -15:
						break;
					case 15:
						{ return new Symbol(SeaSymbol.PLUS); }
					case -16:
						break;
					case 16:
						{ return new Symbol(SeaSymbol.MINUS); }
					case -17:
						break;
					case 17:
						{ return new Symbol(SeaSymbol.ASSIGN); }
					case -18:
						break;
					case 18:
						{ return new Symbol(SeaSymbol.GETPTR); }
					case -19:
						break;
					case 19:
						{ return new Symbol(SeaSymbol.COLON); }
					case -20:
						break;
					case 20:
						{ return new Symbol(SeaSymbol.NOT); }
					case -21:
						break;
					case 21:
						{ return new Symbol(SeaSymbol.COMPLEMENT); }
					case -22:
						break;
					case 22:
						{ yybegin(SINGLE_COMMENT); }
					case -23:
						break;
					case 23:
						{ yybegin(MULTI_COMMENT); }
					case -24:
						break;
					case 24:
						{ return new Symbol(SeaSymbol.U8); }
					case -25:
						break;
					case 25:
						{ return new Symbol(SeaSymbol.I8); }
					case -26:
						break;
					case 26:
						{ return new Symbol(SeaSymbol.IF); }
					case -27:
						break;
					case 27:
						{ return new Symbol(SeaSymbol.QSTRING, yytext());}
					case -28:
						break;
					case 28:
						{ return new Symbol(SeaSymbol.FN); }
					case -29:
						break;
					case 29:
						{ return new Symbol(SeaSymbol.DEREF); }
					case -30:
						break;
					case 30:
						{ return new Symbol(SeaSymbol.EQUALITY); }
					case -31:
						break;
					case 31:
						{ return new Symbol(SeaSymbol.NEQUALITY); }
					case -32:
						break;
					case 32:
						{ return new Symbol(SeaSymbol.SHR); }
					case -33:
						break;
					case 33:
						{ return new Symbol(SeaSymbol.SHL); }
					case -34:
						break;
					case 34:
						{ return new Symbol(SeaSymbol.U64); }
					case -35:
						break;
					case 35:
						{ return new Symbol(SeaSymbol.U32); }
					case -36:
						break;
					case 36:
						{ return new Symbol(SeaSymbol.U16); }
					case -37:
						break;
					case 37:
						{ return new Symbol(SeaSymbol.I64); }
					case -38:
						break;
					case 38:
						{ return new Symbol(SeaSymbol.I32); }
					case -39:
						break;
					case 39:
						{ return new Symbol(SeaSymbol.I16); }
					case -40:
						break;
					case 40:
						{ return new Symbol(SeaSymbol.F64); }
					case -41:
						break;
					case 41:
						{ return new Symbol(SeaSymbol.F32); }
					case -42:
						break;
					case 42:
						{ return new Symbol(SeaSymbol.MUT); }
					case -43:
						break;
					case 43:
						{ return new Symbol(SeaSymbol.LET); }
					case -44:
						break;
					case 44:
						{ return new Symbol(SeaSymbol.NUM, yytext()); }
					case -45:
						break;
					case 45:
						{ return new Symbol(SeaSymbol.NUM, yytext()); }
					case -46:
						break;
					case 46:
						{ return new Symbol(SeaSymbol.ELSE); }
					case -47:
						break;
					case 47:
						{ yybegin(INCLUDE); }
					case -48:
						break;
					case 48:
						{ return new Symbol(SeaSymbol.USIZE); }
					case -49:
						break;
					case 49:
						{ return new Symbol(SeaSymbol.SSIZE); }
					case -50:
						break;
					case 50:
						{ return new Symbol(SeaSymbol.WIDE_CHAR); }
					case -51:
						break;
					case 51:
						{ return new Symbol(SeaSymbol.STATIC); }
					case -52:
						break;
					case 52:
						{ return new Symbol(SeaSymbol.STRUCT); }
					case -53:
						break;
					case 53:
						{ return new Symbol(SeaSymbol.RETURN); }
					case -54:
						break;
					case 54:
						{ }
					case -55:
						break;
					case 55:
						{ yybegin(YYINITIAL); }
					case -56:
						break;
					case 56:
						{ }
					case -57:
						break;
					case 57:
						{ yybegin(YYINITIAL); }
					case -58:
						break;
					case 58:
						{ yybegin(YYINITIAL); return new Symbol(SeaSymbol.INCLUDE, yytext()); }
					case -59:
						break;
					case 60:
						{ return new Symbol(SeaSymbol.error); }
					case -60:
						break;
					case 61:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -61:
						break;
					case 62:
						{ return new Symbol(SeaSymbol.NUM, yytext()); }
					case -62:
						break;
					case 63:
						{ }
					case -63:
						break;
					case 65:
						{ return new Symbol(SeaSymbol.error); }
					case -64:
						break;
					case 66:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -65:
						break;
					case 67:
						{ return new Symbol(SeaSymbol.NUM, yytext()); }
					case -66:
						break;
					case 69:
						{ return new Symbol(SeaSymbol.error); }
					case -67:
						break;
					case 70:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -68:
						break;
					case 72:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -69:
						break;
					case 74:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -70:
						break;
					case 76:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -71:
						break;
					case 78:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -72:
						break;
					case 80:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -73:
						break;
					case 81:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -74:
						break;
					case 82:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -75:
						break;
					case 83:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -76:
						break;
					case 84:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -77:
						break;
					case 85:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -78:
						break;
					case 86:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -79:
						break;
					case 87:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -80:
						break;
					case 88:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -81:
						break;
					case 89:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -82:
						break;
					case 90:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -83:
						break;
					case 91:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -84:
						break;
					case 92:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -85:
						break;
					case 93:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -86:
						break;
					case 94:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -87:
						break;
					case 95:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -88:
						break;
					case 96:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -89:
						break;
					case 97:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -90:
						break;
					case 98:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -91:
						break;
					case 99:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -92:
						break;
					case 100:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -93:
						break;
					case 101:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -94:
						break;
					case 102:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -95:
						break;
					case 103:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -96:
						break;
					case 104:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -97:
						break;
					case 105:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -98:
						break;
					case 106:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -99:
						break;
					case 107:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -100:
						break;
					case 108:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -101:
						break;
					case 109:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -102:
						break;
					case 110:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -103:
						break;
					case 111:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -104:
						break;
					case 112:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -105:
						break;
					case 113:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -106:
						break;
					case 114:
						{ return new Symbol(SeaSymbol.ID, yytext());}
					case -107:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
