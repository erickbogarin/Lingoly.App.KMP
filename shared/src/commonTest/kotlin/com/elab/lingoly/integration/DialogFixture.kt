package com.elab.lingoly.integration

object DialogFixtures {

    val completeJson = """
        {
          "categories": [
            {
              "id": "restaurant",
              "name": {
                "en": "Restaurant",
                "pt": "Restaurante"
              },
              "subcategories": [
                {
                  "id": "ordering_food",
                  "name": {
                    "en": "Ordering Food",
                    "pt": "Fazendo Pedido"
                  },
                  "dialogs": [
                    {
                      "id": "salmon_order",
                      "title": {
                        "en": "Ordering Salmon",
                        "pt": "Pedindo Salmão"
                      },
                      "tags": ["food", "restaurant", "ordering"],
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
                          "translations": {
                            "en": "I'd like the grilled salmon, please.",
                            "pt": "Quero o salmão grelhado, por favor."
                          },
                          "tips": {
                            "en": "Polite way to order",
                            "pt": "Forma educada de pedir"
                          }
                        },
                        {
                          "id": "p2",
                          "role": "WAITER",
                          "translations": {
                            "en": "Would you like any sides?",
                            "pt": "Deseja algum acompanhamento?"
                          },
                          "tips": {
                            "en": "'Sides' means side dishes",
                            "pt": "'Sides' significa acompanhamentos"
                          }
                        }
                      ]
                    }
                  ]
                }
              ]
            }
          ]
        }
    """.trimIndent()
}
