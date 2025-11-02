package com.elab.lingoly.data.local

object DialogJsonFixtures {

    val valid = """
        {
          "categories": [
            {
              "id": "restaurant",
              "name": {"en": "Restaurant", "pt": "Restaurante"},
              "subcategories": [
                {
                  "id": "ordering",
                  "name": {"en": "Ordering", "pt": "Pedindo"},
                  "dialogs": [
                    {
                      "id": "d1",
                      "title": {"en": "Order Salmon", "pt": "Pedir Salmão"},
                      "tags": ["food", "restaurant"],
                      "meta": {
                        "xpReward": 10,
                        "estimatedTime": "45",
                        "difficulty": "easy",
                        "targetLanguage": "en"
                      },
                      "phrases": [
                        {
                          "id": "p1",
                          "role": "CUSTOMER",
                          "translations": {"en": "I'd like salmon", "pt": "Quero salmão"},
                          "tips": {}
                        }
                      ]
                    },
                    {
                      "id": "d2",
                      "title": {"en": "Order Drink", "pt": "Pedir Bebida"},
                      "tags": ["drink"],
                      "meta": {
                        "xpReward": 15,
                        "estimatedTime": "30",
                        "difficulty": "medium",
                        "targetLanguage": "en"
                      },
                      "phrases": [
                        {
                          "id": "p2",
                          "role": "WAITER",
                          "translations": {"en": "What would you like?", "pt": "O que deseja?"},
                          "tips": {}
                        }
                      ]
                    }
                  ]
                }
              ]
            },
            {
              "id": "travel",
              "name": {"en": "Travel", "pt": "Viagem"},
              "subcategories": []
            }
          ]
        }
    """.trimIndent()

    val minimal = """
        {
          "categories": [
            {
              "id": "test",
              "name": {"en": "Test"},
              "subcategories": []
            }
          ]
        }
    """.trimIndent()

    const val empty = """{"categories": []}"""

    const val invalid = """{ "invalid": json }"""

    val withUnknownFields = """
        {
          "unexpectedRootField": "value",
          "categories": [
            {
              "id": "restaurant",
              "unknownField": 123,
              "name": {"en": "Restaurant", "pt": "Restaurante"},
              "subcategories": []
            }
          ],
          "anotherWeirdField": {"nested": true}
        }
    """.trimIndent()
}
