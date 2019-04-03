#ifndef __SEA_INTERNAL_H__
#define __SEA_INTERNAL_H__

#include "sea_node.h"

enum seavalue_e {
    SVL_TOKEN,
    SVL_STRING,
    SVL_CHILDREN,
};

union seavalue_u {
    int token;
    char* string;
    struct SeaNode** children;
};

struct seavalue_t {
    enum seavalue_e type;
    union seavalue_u data;
};

typedef struct SeaNode {
    enum NonTerminal type;
    // TODO: replace this with SeaValue for identifiers/qstrings
    struct seavalue_t val;
} SeaNode;

#endif // __SEA_INTERNAL_H__